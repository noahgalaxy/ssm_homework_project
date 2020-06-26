package com.fisheep.db;
import com.alibaba.fastjson.JSON;
import com.fisheep.bean.Belong;
import com.fisheep.bean.Group;
import com.fisheep.bean.Homework;
import com.fisheep.dao.BelongMapper;
import com.fisheep.dao.GroupMapper;
import com.fisheep.dao.HomeworkMapper;
import org.apache.commons.beanutils.ConvertUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.*;

import javax.lang.model.util.AbstractElementVisitor6;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
//        for (Object object : objects) {
//            System.out.println(object);
//        }
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
    public void testGetHomeworkWithGroups(){
        jedis = jedisPool.getResource();
        Pipeline pipelined = jedis.pipelined();

        Response<Map<String, String>> response = pipelined.hgetAll("homework:40");
        Response<String> stringResponse = pipelined.get("belong:40");
        pipelined.sync();
        System.out.println(response.toString());
        System.out.println(response.get().toString());
        System.out.println(stringResponse.get());
    }

    @Test
    public void testGroupListCache(){
        jedis = jedisPool.getResource();
        Pipeline pipelined = jedis.pipelined();

        List<Homework> homeworkList = homeworkMapper.getAllHomeworksWithGroups();
        System.out.println(homeworkList);
        Map<String, String> map = new HashMap<>();
        for (Homework homework : homeworkList) {
            map.clear();
            System.out.println(homework);

            String homeworoId = Integer.toString(homework.getHomeworkId());
            map.put("homeworkId", homeworoId);
            map.put("homeworkName", homework.getHomeworkName());
            map.put("homeworkCode", homework.getHomeworkCode());
            map.put("homeworkDead", homework.getHomeworkDead());
            map.put("homeworkCreatorId", Integer.toString(homework.getHomeworkCreatorId()));
            map.put("homeworktotalnums", Integer.toString(homework.getHomeworktotalnums()));
            map.put("location", homework.getLocation());
            map.put("homeworksubmittednums", Integer.toString(homework.getHomeworksubmittednums()));
            map.put("expired", homework.isExpired() == true ? "1": "0");

            List<Group> groups = homework.getGroups();

            String groupsString = groups.size() == 0? "-": JSON.toJSONString(groups);
            System.out.println("groupsString"+groupsString);

            map.put("groups", groupsString);

            //键结构  homework：HomeworkCreatorId ：HomeworkId
            pipelined.hmset("homework:"+Integer.toString(homework.getHomeworkCreatorId())+":"+
                    Integer.toString(homework.getHomeworkId()), map);
        }
        pipelined.sync();
        pipelined.close();
    }

    @Test
    public void testGetHomeworksFromCache() throws IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        jedis = jedisPool.getResource();
        Pipeline pipelined = jedis.pipelined();
        Response<Set<String>> keys = pipelined.keys("homework:2:*");
        pipelined.sync();
        pipelined.close();

        System.out.println(keys.get());

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
            Homework homework2 = (Homework) Class.forName("com.fisheep.bean.Homework").newInstance();
            while (entryIterator.hasNext()){
                Map.Entry entry = (Map.Entry) entryIterator.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();

                System.out.println("key:"+key+"\nvalue:"+value.toString());

                if(key.equals("groups") && ((String)value).equals("-")) continue;
                if(key.equals("groups")  && !((String)value).equals("-")){
                    List<Group> groupList = (List<Group>) JSON.parse((String) value);
                    value = groupList;
                }

                if(key.equals("expired")){
                    value = value.equals("0")? false:true;
                }

                Field declaredField = homework2.getClass().getDeclaredField(key);

                //                    String typeName = declaredField.getGenericType().getTypeName();
                declaredField.setAccessible(true);
                declaredField.set(homework2, ConvertUtils.convert(value, declaredField.getType()));
                declaredField.setAccessible(false);

            }
            homeworkList.add(homework2);
            System.out.println("=============================");
            }
        System.out.println(homeworkList);
        for (Homework homework : homeworkList) {
            System.out.println(homework);
        }
    }

    @Test
    public void testDeleteRedis(){
        Jedis jedis = jedisPool.getResource();
        Pipeline pipeline = jedis.pipelined();
        for (int i = 1; i <= 3 ; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", Integer.toString(i));
            pipeline.hset("test:"+Integer.toString(i), hashMap);
        }
        pipeline.sync();
        System.out.println("====================================");

        for (int i = 1; i <= 3; i++) {
            pipeline.keys("test:"+Integer.toString(i));
        }

        List<Object> keysList1 = pipeline.syncAndReturnAll();
        System.out.println(keysList1);


        for (Object key : keysList1) {
            System.out.println(key.getClass().getName());
            if(Set.class.isInstance(key)){
                String next = (String) ((Set) key).iterator().next();
                System.out.println(next);
                pipeline.hget(next, "name");
                pipeline.del(next);
            }
        }
        System.out.println("++++++++++++++++++++++++++++++++");
        List<Object> objectList = pipeline.syncAndReturnAll();
        for (Object o : objectList) {
            System.out.println(o.getClass().getName());
            System.out.println(o.toString());
        }

    }

    @Test
    public void testGetUidHomeworklist(){
        Set<String> keys = jedis.keys("homework:" + 2 + ":*");
        System.out.println(keys.size());
        Iterator<String> keysIterator = keys.iterator();

        while (keysIterator.hasNext()){
            String key = keysIterator.next();
            System.out.println(key);
        }
    }

    @Test
    public void testPilelineHmset(){
        Jedis jedis = jedisPool.getResource();
        Pipeline pipelined = jedis.pipelined();
        Map<String, String> map = new HashMap<>();
        map.put("name", "xiaoming");
        map.put("age", "22");
        pipelined.hmset("test:5", map);
        List<Object> syncAndReturnAll = pipelined.syncAndReturnAll();
        System.out.println(syncAndReturnAll.toString());
    }

}
