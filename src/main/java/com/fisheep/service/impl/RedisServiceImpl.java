package com.fisheep.service.impl;

import com.alibaba.fastjson.JSON;
import com.fisheep.bean.Group;
import com.fisheep.bean.Homework;
import com.fisheep.service.RedisService;
import com.fisheep.utils.RedisUtil;
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
        Pipeline pipelined = jedis.pipelined();
        Response<Set<String>> keys = pipelined.keys("homework:"+uid+":*");
        pipelined.sync();
        pipelined.close();

//        System.out.println(keys.get());

        Pipeline pipelined1 = jedis.pipelined();
        for (String key : keys.get()) {
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
}
