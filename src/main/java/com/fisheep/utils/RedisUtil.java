package com.fisheep.utils;

import com.alibaba.fastjson.JSON;
import com.fisheep.bean.Group;
import com.fisheep.bean.Homework;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RedisUtil {

    public static Homework redisHomeworkToObject(Map homeworkMap){
        if(homeworkMap == null){
            return null;
        }
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
            try {
                declaredField.set(homework2, ConvertUtils.convert(value, declaredField.getType()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            declaredField.setAccessible(false);
        }
        return homework2;
    }

    public static Map<String, String> homeworkToRedisMap(Homework homework){
        Map<String, String> map= new HashMap<>();
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
        return map;
    }

    public static Long parseDateToUnixTime(String date){

        if(date == null){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date1;
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date1.getTime();
    }
}
