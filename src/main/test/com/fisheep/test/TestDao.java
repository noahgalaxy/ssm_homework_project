package com.fisheep.test;


import com.fisheep.bean.UserHasGroup;
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
    public void testUserHasGroupMapperGetGroupsByUid(){
        List<UserHasGroup> groups = userHasGroupMapper.getGroupsByUid(22);
        System.out.println(groups);
        System.out.println(groups.isEmpty());
        System.out.println(null == groups);
        System.out.println(groups.size());
        if(groups.size() > 0){
            for(UserHasGroup uhg: groups){
                System.out.println(uhg);
            }
        }
    }
}
