package com.fisheep.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fisheep.bean.Group;
import com.fisheep.bean.Homework;
import com.fisheep.dao.GroupMapper;
import com.fisheep.service.RedisService;
import com.fisheep.utils.RedisUtil;
import com.fisheep.utils.StringToNum;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    JedisPool jedisPool;

    @Autowired
    GroupMapper groupMapper;

    @Override
    public Boolean getExpired(String homeworkCode) {
        Jedis jedis = jedisPool.getResource();
        Boolean exists = jedis.exists("code_id:"+homeworkCode);
        System.out.println("redis查询 "+homeworkCode +" 存在与否？ "+exists);
        jedis.close();
        return exists;
    }

    @Override
    public List<Homework> getHomeworksWithGroupsByUid(int uid) throws IllegalAccessException {
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("homework:" + uid + ":*");
        Iterator<String> keysIterator = keys.iterator();
        if(!keysIterator.hasNext()){
            System.out.println("getHomeworksWithGroupsByUid取不到"+Integer.toString(uid)+"缓存，返回null");
            return null;
        }

//        System.out.println(keys.get());

        Pipeline pipelined1 = jedis.pipelined();

        while (keysIterator.hasNext()){
            String key = keysIterator.next();
            pipelined1.hgetAll(key);
        }

        List<Object> homeworks = pipelined1.syncAndReturnAll();
        pipelined1.close();
        List<Homework> homeworkList = new ArrayList<>();
        for (Object homework : homeworks) {
            Map homeworkMap = (HashMap) homework;
//            System.out.println((homework.getClass().getName()));
            //将redis查询出来的hash对象转换成Homework对象
            Homework homework1 = RedisUtil.redisHomeworkToObject(homeworkMap);
            homeworkList.add(homework1);
//            System.out.println("=============================");
        }
        return homeworkList;
    }

    @Override
    public Homework getHomeworkByHomeId(Integer homeworkId) {
        Jedis jedis = jedisPool.getResource();

        Set<String> keys = jedis.keys("homework:*:" + homeworkId.toString());
//        redis缓存命中，且键唯一
        if(!keys.isEmpty() && keys.size() == 1){
            Map<String, String> homeworkMap = jedis.hgetAll(keys.iterator().next());
            // 空值处理再RedisUtil里面进行
            Homework homework = RedisUtil.redisHomeworkToObject(homeworkMap);
            return homework;
        }
        return null;
    }

    @Override
    public void insertHomework(Homework homework) {
        Map<String, String> homeworkMap = RedisUtil.homeworkToRedisMap(homework);
        Jedis jedis = jedisPool.getResource();
        jedis.hmset("homework:"+Integer.toString(homework.getHomeworkCreatorId())+":"+
                Integer.toString(homework.getHomeworkId()), homeworkMap);
        System.out.println("jedis 插入缓存 insertHomework");
    }

    /**
     * 如果没有时间，则不设置过期，即是永不过期
     * @param homeworkCode
     * @param homeworkId
     * @param homeworkDead
     */
    @Override
    public void insertCodeIdExpire(String homeworkCode, int homeworkId, String homeworkDead) {
        Long unixTime = RedisUtil.parseDateToUnixTime(homeworkDead);
        Jedis jedis = jedisPool.getResource();
        if(unixTime != null){
            Pipeline pipelined = jedis.pipelined();
            pipelined.set("code_id:"+homeworkCode, Integer.toString(homeworkId));
            pipelined.pexpireAt("code_id:"+homeworkCode, unixTime);
            pipelined.sync();
            pipelined.close();
        }else {
            jedis.setnx("code_id:"+homeworkCode, Integer.toString(homeworkId));
        }
    }

    /**
     * 跟据homeworkId删除缓存；
     * @param homeworkId
     */
    @Override
    public void deleteHomeworkById(int homeworkId) {
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("homework:*" + Integer.toString(homeworkId));
        if(!keys.isEmpty()){
            Pipeline pipeline = jedis.pipelined();
            Iterator<String> keyIterator = keys.iterator();
            while (keyIterator.hasNext()){
                pipeline.del(keyIterator.next());
            }
            pipeline.sync();
            System.out.print("删除成功\t");
        }
        System.out.println("删除keys:"+keys.toString());
    }

    @Override
    public void deleteHomeworkByIdOrBatchId(List<Integer> idList) {
        /*
        1.遍历idList，用pipeline从redis里面取出所有key
        2.用pipeline取出所有homeworkCode
        3.pipeline删除keys
         */
        Jedis jedis = jedisPool.getResource();
        Pipeline pipeline = jedis.pipelined();
        for (Integer id : idList) {
            String keyPattern = "homework:*"+Integer.toString(id);
            pipeline.keys(keyPattern);
        }

        List<Object> keysList = pipeline.syncAndReturnAll();
        System.out.println("keysList: "+keysList.toString());

        for (Object key : keysList) {
            System.out.println("遍历删除homework： "+key.toString()+"\t"+key.getClass().getName());
            if(Set.class.isInstance(key) && !((Set) key).isEmpty()){
                Set key1 = (Set) key;
                String stringKey = (String) key1.iterator().next();
                pipeline.hget(stringKey, "homeworkCode");
                pipeline.del(stringKey);
            }

        }
        List<Object> codeList = pipeline.syncAndReturnAll();
        System.out.println("codeList: "+codeList.toString());
        //pipeline删除homework和code_id:
        for (Object code : codeList) {
            System.out.println("遍历删除code_id: "+code.toString()+"\t"+code.getClass().getName());
            if(String.class.isInstance(code)){
                pipeline.del("code_id:"+code);
            }
        }
        pipeline.sync();
        pipeline.close();
    }

    @Override
    public void deleteHomeworkByIdOrBatchId(int homeworkId) {
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(homeworkId);
        this.deleteHomeworkByIdOrBatchId(arrayList);
    }

    /*
    主要更新的有：
        homework:creatorId:homeworkId中的：
            homeworkName    homeworktotalnums   homeworkDead    groups
        如果homeworkDead改变的话还需要更新：code_id
     */
    @Override
    public void updateHomework(Homework homework) {
        String groupString = "-";
        String homeworkName = homework.getHomeworkName();
        String homeworkDead = homework.getHomeworkDead();
        int homeworktotalnums = homework.getHomeworktotalnums();
        long unixTime = -1;
        Map<String, String> updatedFieldsMap = new HashMap<>();
        List<Integer> list = StringToNum.numStringToSingleNum(homework.getGroupsIdString());
        if(list != null){
            if(!list.isEmpty()){
                List<Group> groupList = groupMapper.selectGroupsByGroupIdsList(list);
                groupString = JSON.toJSONString(groupList);
                System.out.println("updateHomework查询出来的："+groupString);
            }
        }
        updatedFieldsMap.put("groups", groupString);
        if(homeworkName != null){
            updatedFieldsMap.put("homeworkName", homeworkName);
        }
        if(homeworktotalnums != 0){
            updatedFieldsMap.put("homeworktotalnums", Integer.toString(homeworktotalnums));
        }
        if(homeworkDead != null){
            updatedFieldsMap.put("homeworkDead", homeworkDead);
            unixTime = RedisUtil.parseDateToUnixTime(homeworkDead);
        }
        System.out.println("缓存更新updateHomework："+updatedFieldsMap);
        Jedis jedis = jedisPool.getResource();
        Pipeline pipeline = jedis.pipelined();
        pipeline.hmset("homework:"+Integer.toString(homework.getHomeworkCreatorId())+":"+Integer.toString(homework.getHomeworkId()),
                updatedFieldsMap);
        if(unixTime>0){
            pipeline.pexpireAt("code_id:"+homework.getHomeworkCode(),unixTime);
        }

        List<Object> syncAndReturnAll = pipeline.syncAndReturnAll();
        System.out.println("缓存修改结果：\n"+syncAndReturnAll.toString());
    }
}
