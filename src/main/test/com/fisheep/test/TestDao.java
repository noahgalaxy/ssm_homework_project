package com.fisheep.test;


import com.fisheep.bean.Belong;
import com.fisheep.bean.Homework;
import com.fisheep.bean.UserHasGroup;
import com.fisheep.dao.BelongMapper;
import com.fisheep.dao.HomeworkMapper;
import com.fisheep.dao.UserHasGroupMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springContext.xml"})
public class TestDao {
    @Autowired
    UserHasGroupMapper userHasGroupMapper;
    @Autowired
    BelongMapper belongMapper;

    @Autowired
    HomeworkMapper homeworkMapper;

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

    @Test
    public void testBelongMapperInsertBelong(){
        Homework homework = new Homework("气息F","SDFACE", "2020-9-12 16:55",
                "test", 2, 70);
        int rowsAffected = homeworkMapper.insertHomework(homework);
        System.out.println("作业插入的id："+homework.getHomeworkId()+"\nrowsAffected:"+rowsAffected);

        List<Belong> list = new ArrayList<>();

        list.add(new Belong(homework.getHomeworkId(), 5));
        list.add(new Belong(7, 5));

        System.out.println(list.toString());
        System.out.println("插入:"+(belongMapper.insertBelong(list)));
    }

    @Test
    public void testNull(){
        List<Belong> list = new ArrayList<>();
        System.out.println(list.size());
    }
}
