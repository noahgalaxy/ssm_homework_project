package com.fisheep.controller;

import com.fisheep.bean.Homework;
import com.fisheep.service.HomeworkService;
import com.fisheep.service.impl.HomeworkServiceImpl;
import com.fisheep.utils.Msg;
import com.fisheep.utils.SnowAlgorithum;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeworkController {
    @Autowired
    @Qualifier("homeworkServiceImpl")
    HomeworkService homeworkServiceImpl;

    @RequestMapping(path = "/homeworkRelease")
    @ResponseBody
    public Msg homeworkRelease(Homework homework, HttpSession session){
        int uid;
        if(session.isNew()){
            System.out.println("session is new!! ");
            return Msg.fail();
        }
        try {
            uid = (int) session.getAttribute("uid");
            homework.setHomeworkCreatorId(uid);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        //1.从session里面取出uid，作为此作业的发布者id，即是creatorId；
//        Integer uid = (int)session.getAttribute("uid");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        try {
//            Date date = sdf.parse(homework.getHomeworkDead());
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return Msg.fail();
//        }
        System.out.println("  发布的作业：\n"+homework);
        //获取一个code放入homework对象里面；
        homework.setHomeworkCode(SnowAlgorithum.getCode());
        homework.setLocation("upload");
        System.out.println("--------------\n"+homework);
        int rowsAffected = homeworkServiceImpl.insertHomework(homework);
        System.out.println("HomeworkController rowsAffected:"+ rowsAffected);
        if(rowsAffected > 0){
            return Msg.success();
        }
        return Msg.fail();
    }
}
