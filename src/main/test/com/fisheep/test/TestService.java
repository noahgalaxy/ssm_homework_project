package com.fisheep.test;


import com.fisheep.bean.Homework;
import com.fisheep.service.HomeworkAndBelongService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:springContext.xml", "classpath:springMVC.xml"})
public class TestService {
    @Autowired
    HomeworkAndBelongService homeworkAndBelongServiceImpl;

    @Test
    public void testHomeWorkAndBelongService(){
        Homework homework = new Homework("事务控制A","564Ss","2020-5-25 21:58", "test", 2, 66);
        homework.setGroupsIdString("5-6");
        boolean flag = homeworkAndBelongServiceImpl.insertHomeworkAndBelong(homework);
        if(flag){
            System.out.println("success!");
        }else {
            System.out.println("fail");
        }
    }
}
