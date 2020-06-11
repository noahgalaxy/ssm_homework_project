package com.fisheep.db;


import com.fisheep.bean.Homework;
import com.fisheep.service.HomeworkService;
import com.fisheep.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.util.List;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:springContext.xml"})
public class Cache {
    @Autowired
    JedisPool jedisPool;

    @Autowired
    RedisService redisServiceImpl;
    @Autowired
    HomeworkService homeworkServiceImpl;

    @Test
    public void testCacheQuery(){
        try {
            List<Homework> homeworks = redisServiceImpl.getHomeworksWithGroupsByUid(2);
            for (Homework homework : homeworks) {
                System.out.println(homework);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("=======================================");
        List<Homework> homeworkList = homeworkServiceImpl.getHomeworksWithGroupsByUid(2);
        for (Homework homework : homeworkList) {
            System.out.println(homework);
        }
    }
}
