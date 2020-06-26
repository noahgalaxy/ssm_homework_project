package com.fisheep.test;


import com.fisheep.bean.*;
import com.fisheep.dao.*;
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

    @Autowired
    SubmitMapper submitMapper;

    @Autowired
    GroupMapper groupMapper;


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

    @Test
    public void testGetHomeworksWithGroupsByUid(){
        List<Homework> homeworkList = homeworkMapper.getHomeworksWithGroupsByUid(4);
        System.out.println(homeworkList==null); //空列表也不是null；
        System.out.println(homeworkList.size());//而是size==0；
        for (Homework homework: homeworkList){
            System.out.println(homework);
        }
    }
    @Test
    public void testDeleteHomeworkById(){
        System.out.println(homeworkMapper.deleteHomeworkById(1));
    }

    @Test
    public void testDeleteHomeworkByBatchId(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(3);
        System.out.println(homeworkMapper.deleteHomeworkByBatchId(list));
    }
    @Test
    public void testGetHomeworkByHomeId(){
        Homework homework = homeworkMapper.getHomeworkByHomeId(33);
        System.out.println(null == homework);
        System.out.println(homework);
    }

    @Test
    public void testUpdateHomework(){
        Homework homework = new Homework();
        homework.setHomeworkId(36);
        homework.setHomeworkName("更新作业B");
        homework.setHomeworktotalnums(10000);
        homework.setHomeworkDead("2055-9-21 20：");
        int rowsAffected = homeworkMapper.updateHomework(homework);
        System.out.println("更新后受影响的行："+rowsAffected);
    }

    @Test
    public void  testGetHomeworkExpiredByHomeCode(){
        Boolean expired = homeworkMapper.gethomeworkExpiredByHomeCode("w0iNs82a");
        System.out.println(expired);
    }

    @Test
    public void testUpdateSubmit(){
        Submit submit = new Submit("余扬", 4, " 31f25b", "new fasdile", "location");
        submitMapper.updateSubmit(submit);
    }

    @Test
    public void testGetAllBelong(){
        List<Belong> allBelong = belongMapper.getAllBelong();
        System.out.println(allBelong);
        for (Belong belong : allBelong) {
            System.out.println("belong:"+Integer.toString(belong.getBelongHomweorkId()));
        }
    }

    @Test
    public void testSelectGroupsByGroupIdsList(){
        List<Integer> groupIdsList = new ArrayList<>();
        groupIdsList.add(5);
        groupIdsList.add(6);
        groupIdsList.add(10);
        List<Group> groupList = groupMapper.selectGroupsByGroupIdsList(groupIdsList);
        System.out.println(groupList);
    }

    @Test
    public void testGetSubmittedFilesByHomeworkId(){
//        List<Submit> submits = submitMapper.getSubmittedFilesByHomeworkId(29);
        String homeworkNameById = homeworkMapper.getHomeworkNameById(29);
        System.out.println(homeworkNameById); }

}

