package com.fisheep.controller;

import com.fisheep.bean.Belong;
import com.fisheep.bean.Homework;
import com.fisheep.service.BelongService;
import com.fisheep.service.HomeworkService;
import com.fisheep.service.impl.HomeworkServiceImpl;
import com.fisheep.utils.Msg;
import com.fisheep.utils.SnowAlgorithum;
import com.fisheep.utils.StringToNum;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeworkController {
    @Autowired
    @Qualifier("homeworkServiceImpl")
    HomeworkService homeworkServiceImpl;

    @Autowired
    BelongService belongServiceImpl;

    @RequestMapping(path = "/homeworkRelease")
    @ResponseBody
    public Msg homeworkRelease(Homework homework, HttpSession session){
        System.out.println("  发布的作业：\n"+homework);
        System.out.println("groupsIdString:"+homework.getGroupsIdString());
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
        if(homework.getHomeworkName() == null || homework.getHomeworkCreatorId() == 0 ||
                homework.getHomeworkDead() == null || homework.getHomeworktotalnums() == 0){
            System.out.println("字段空");
            return Msg.fail();
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

        //获取一个code放入homework对象里面；
        homework.setHomeworkCode(SnowAlgorithum.getCode());
        homework.setLocation("upload");
        System.out.println("--------------\n"+homework);
        int rowsAffected = homeworkServiceImpl.insertHomework(homework);
        System.out.println("HomeworkController rowsAffected:"+ rowsAffected);
        //处理作业所属组字符串，返回由单个数字组成的列表
        List<Integer> groupsIdsList = StringToNum.numStringToSingleNum(homework.getGroupsIdString());
        System.out.println("groupsIdsList:"+groupsIdsList.toString());
        if (null != groupsIdsList){
            List<Belong> belongList = new ArrayList<>();
            for(int num: groupsIdsList){
                belongList.add(new Belong(homework.getHomeworkId(), num));
            }
            int belongRowsAffected = belongServiceImpl.insertBelong(belongList);
            System.out.println("belongRowsAffected:"+belongRowsAffected+"\tgroupsIdsList.size():"+groupsIdsList.size());
            if(belongRowsAffected != groupsIdsList.size()){
                return Msg.fail();
            }
        }
//        homeworkServiceImpl.insertBelong()
        if(rowsAffected > 0 ){
            return Msg.success();
        }
        return Msg.fail();
    }
}
