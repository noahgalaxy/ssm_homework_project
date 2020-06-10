package com.fisheep.db;
import com.alibaba.fastjson.JSON;
import com.fisheep.bean.Belong;
import com.fisheep.bean.Group;
import com.fisheep.bean.Homework;
import com.fisheep.dao.BelongMapper;
import com.fisheep.dao.GroupMapper;
import com.fisheep.dao.HomeworkMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarOutputStream;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springContext.xml", "classpath:springMVC.xml"})
public class Redis {

    private Jedis jedis;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    /**
     * 注入连接池
     */
    @Autowired
    HomeworkMapper homeworkMapper;

    @Autowired
    JedisPool jedisPool;

    @Autowired
    BelongMapper belongMapper;

    @Autowired
    GroupMapper groupMapper;


    @Before
    public void createRedisLink(){
        jedis = jedisPool.getResource();
//        jedis = new Jedis("120.26.179.169", 6380);
    }

    @After
    public void closeRedisLink(){
        if(jedis != null){
            System.out.println("jedis closed");
            jedis.close();
        }
    }

    @Test
    public void redisMemchace() throws ParseException {
        /*
        将所有homework查出来，其homeworkid作为键，属性所谓生成map，存入redis作为缓存
         */
//        Jedis jedis = new Jedis("120.26.179.169", 6380);
        System.out.println(jedis.ping());
        List<Homework> allHomeworks = getAllHomeworks();
        Map<String, String> objMap= new HashMap<>();
        Pipeline pipelined = jedis.pipelined();
        for (Homework homework : allHomeworks) {
            objMap.clear();
//            System.out.println(homework);
            String homeworoId = Integer.toString(homework.getHomeworkId());
            pipelined.watch(homeworoId);
            objMap.put("homeworoId", homeworoId);
            objMap.put("homeworkName", homework.getHomeworkName());
            objMap.put("homeworkCode", homework.getHomeworkCode());
            objMap.put("homeworkDead", homework.getHomeworkDead());
            objMap.put("homeworkCreatorId", Integer.toString(homework.getHomeworkCreatorId()));
            objMap.put("homeworkTotalNums", Integer.toString(homework.getHomeworktotalnums()));
            objMap.put("location", homework.getLocation());
            objMap.put("homeworksubmittednums", Integer.toString(homework.getHomeworksubmittednums()));
            objMap.put("expired", homework.isExpired() == true ? "1": "0");
            //            直接gettime获取的是unix时间戳的毫秒形式，/1000可以换成s
            System.out.println(objMap);
            pipelined.hmset("homework:"+homeworoId, objMap);
//            //1.监视keys
//            jedis.watch(homeworoId);
//            //2.开启事务
//            Transaction transaction = jedis.multi();
//            //3.事务体
//            transaction.hmset(homeworoId, objMap);
////            transaction.expireAt(homeworoId, unixTime);
//            transaction.pexpireAt(homeworoId, unixTime);
//            //4.执行事务
//            List<Object> exec = transaction.exec();
//            System.out.println(exec.isEmpty() ? "redis事务执行失败":"redis事务执行成功");
//            //5.解除监视
//            jedis.unwatch();
        }
//        将管道提交到redis
//        Response<List<Object>> exec = pipelined.exec();
//        System.out.println(exec);
        pipelined.sync();
        System.out.println("homeworks 插入redis完成");

        List<Belong> allBelongs = belongMapper.getAllBelong();
        for (Belong allBelong : allBelongs) {
            pipelined.set("belong:"+Integer.toString(allBelong.getBelongHomweorkId()), Integer.toString(allBelong.getBelongGroupId()));
        }
//        Response<List<Object>> exec1 = pipelined.exec();
//        System.out.println(exec1);
        pipelined.sync();
        System.out.println("belong 插入redis完成");

        List<Group> allGroups = groupMapper.getAllGroups();
        for (Group allGroup : allGroups) {
            objMap.clear();
            objMap.put("groupId", Integer.toString(allGroup.getGroupId()));
            objMap.put("groupName", allGroup.getGroupName());
            objMap.put("groupCode", allGroup.getGroupCode());
            objMap.put("creatorId", Integer.toString(allGroup.getCreatorId()));
            pipelined.hmset("group:"+Integer.toString(allGroup.getGroupId()), objMap);
        }
//        Response<List<Object>> exec2 = pipelined.exec();
//        System.out.println(exec2);
        pipelined.sync();
        System.out.println("group 进入redis完成");


    }

    @Test
    public void uploadHomeworkCodeToRedis(){
        String auth = jedis.auth("Fisheep");
        System.out.println("redis auth："+auth);
        System.out.println(jedis.ping());
        List<Homework> allHomeworks = getAllHomeworks();

        Pipeline pipelined = jedis.pipelined();
        for (Homework homework : allHomeworks) {
            Date date = null;
            try{
                date = dateFormat.parse(homework.getHomeworkDead());
            }catch(ParseException e){
                System.out.println("出错的时间格式："+homework.getHomeworkDead());
                continue;
            }
            //            直接gettime获取的是unix时间戳的毫秒形式，/1000可以换成s
            long unixTime = date.getTime();
            String key = "code_id:"+homework.getHomeworkCode();
            pipelined.set(key,Integer.toString(homework.getHomeworkId()));
            pipelined.pexpireAt(key, unixTime);
        }

//        pipelined.sync();
        List<Object> objects = pipelined.syncAndReturnAll();
        for (Object object : objects) {
            System.out.println(object);
        }
    }

    @Test
    public void testUnixTime() throws ParseException {
        Date date = dateFormat.parse("2020-06-26 13:55");

        System.out.println(date.getTime() / 1000);

        Date date1 = new Date();
        System.out.println(date1.getTime() / 1000);
        System.out.println(System.currentTimeMillis());
    }
    @Test
    public void readMapMemcho(){
        Map<String, String> obj = jedis.hgetAll("4");
        System.out.println(obj.toString());
        String homeworkCreatorId = jedis.hget("4", "homeworkCreatorId");
        System.out.println(homeworkCreatorId);
    }

    public List<Homework> getAllHomeworks(){
        List<Homework> allHomeworks = homeworkMapper.getAllHomeworks();
        System.out.println(allHomeworks);
        return allHomeworks;
    }

    @Test
    public void testHashToObject(){
        Map<String, String> testHash = jedis.hgetAll("testHash");
        System.out.println(testHash);
    }

    @Test
    public void saveDataToRedisCache(){
        List<Homework> allHomeworks = getAllHomeworks();
        Pipeline pipeline = jedis.pipelined();
        Map<String, Object> map = new HashMap<>();
        for (Homework allHomework : allHomeworks) {
//            map.put()
        }
    }
}
