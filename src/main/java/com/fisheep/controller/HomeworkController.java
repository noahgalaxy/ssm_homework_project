package com.fisheep.controller;

import com.fisheep.bean.Belong;
import com.fisheep.bean.Homework;
import com.fisheep.service.BelongService;
import com.fisheep.service.HomeworkAndBelongService;
import com.fisheep.service.HomeworkService;
import com.fisheep.service.impl.HomeworkServiceImpl;
import com.fisheep.utils.Msg;
import com.fisheep.utils.SnowAlgorithum;
import com.fisheep.utils.StringToNum;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
//@Transactional(rollbackFor = {Exception.class})
public class HomeworkController {


    @Autowired
    HomeworkAndBelongService homeworkAndBelongServiceImpl;

    @RequestMapping(path = "/homeworkRelease")
    @ResponseBody
    public Msg homeworkRelease(Homework homework, HttpSession session){
        System.out.println("  发布的作业：\n"+homework);
        System.out.println("groupsIdString:"+homework.getGroupsIdString() + "type:"+homework.getGroupsIdString().getClass());
        int uid;
        if(session.isNew()){
            System.out.println("session is new!! ");
            return Msg.fail();
        }
        try {
            //session里面取出uid
            uid = (int) session.getAttribute("uid");
            homework.setHomeworkCreatorId(uid);
        }catch (NullPointerException e){
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }
        //作业名字不能为空，发布者id不能为0或空，截止日期不能为空，作业全部数量不能为空
        if(homework.getHomeworkName() == "" || homework.getHomeworkCreatorId() == 0 ||
                homework.getHomeworkDead() == "" || homework.getHomeworktotalnums() == 0){
            System.out.println("字段空");
            return Msg.fail();
        }

        //获取一个code放入homework对象里面；
        homework.setHomeworkCode(SnowAlgorithum.getCode());
        //设置存放目录
        homework.setLocation("upload");
        System.out.println("--------------\n"+homework);

        boolean flag = homeworkAndBelongServiceImpl.insertHomeworkAndBelong(homework);
        if(!flag){
            return Msg.fail();
        }
        return Msg.success();

    }
}
