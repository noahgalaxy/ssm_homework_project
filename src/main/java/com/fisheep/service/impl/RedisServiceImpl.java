package com.fisheep.service.impl;

import com.fisheep.bean.Homework;
import com.fisheep.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

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
    public List<Homework> getHomeworksWithGroupsByUid(int uid) {
        return null;
    }
}
