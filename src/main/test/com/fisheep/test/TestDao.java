package com.fisheep.test;


import com.fisheep.dao.UserHasGroupMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springContext.xml"})
public class TestDao {
    @Autowired
    UserHasGroupMapper userHasGroupMapper;

    @Test
    public void testUserHasGroupMapperGetGroupByUid(){
        List<Map<Integer, String>> groups = userHasGroupMapper.getGroupByUid(4);
        System.out.println(groups);
        if(groups.size() > 0){
            Map<Integer, String> groupsMap = new HashMap<Integer, String>();
            for(Map<Integer, String> map: groups){
                for(Integer key: map.keySet()){
                    System.out.println(key);
                    groupsMap.put(key, map.get(key));
                }
            }
        }
    }
}
