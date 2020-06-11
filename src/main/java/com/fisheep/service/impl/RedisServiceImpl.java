package com.fisheep.service.impl;

import com.alibaba.fastjson.JSON;
import com.fisheep.bean.Group;
import com.fisheep.bean.Homework;
import com.fisheep.service.RedisService;
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

            Iterator entryIterator = homeworkMap.entrySet().iterator();
            Homework homework2 = null;
            try {
                homework2 = (Homework) Class.forName("com.fisheep.bean.Homework").newInstance();
            } catch (Exception e) {
                return null;
            }
            while (entryIterator.hasNext()){
                Map.Entry entry = (Map.Entry) entryIterator.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();

//                System.out.println("key:"+key+"\nvalue:"+value.toString());

                if(key.equals("groups") && ((String)value).equals("-")) continue;
                if(key.equals("groups")  && !((String)value).equals("-")){
                    List<Group> groupList = (List<Group>) JSON.parse((String) value);
                    value = groupList;
                }

                if(key.equals("expired")){
                    value = value.equals("0")? false:true;
                }

                Field declaredField = null;
                try {
                    declaredField = homework2.getClass().getDeclaredField(key);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    continue;
                }

                //                    String typeName = declaredField.getGenericType().getTypeName();
                declaredField.setAccessible(true);
                declaredField.set(homework2, ConvertUtils.convert(value, declaredField.getType()));
                declaredField.setAccessible(false);

            }
            homeworkList.add(homework2);
//            System.out.println("=============================");
        }
//        System.out.println(homeworkList);
//        for (Homework homework : homeworkList) {
//            System.out.println(homework);
//        }
        return homeworkList;
    }
}
