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
        System.out.println("groupsIdString:"+homework.getGroupsIdString() + "type:"+homework.getGroupsIdString().getClass());
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

        //获取一个code放入homework对象里面；
        homework.setHomeworkCode(SnowAlgorithum.getCode());
        //设置存放目录
        homework.setLocation("upload");
        System.out.println("--------------\n"+homework);
        int rowsAffected = homeworkServiceImpl.insertHomework(homework);
        System.out.println("HomeworkController lastInsertId:"+ rowsAffected);
        //处理作业所属组字符串，返回由单个数字组成的列表
        List<Integer> groupsIdsList = StringToNum.numStringToSingleNum(homework.getGroupsIdString());
        System.out.println("groupsIdsList:"+groupsIdsList.toString()+"\nsize:"+groupsIdsList.size());
        //返回的列表不为空，且长度大于0，有值才能插入。
        if (null != groupsIdsList && groupsIdsList.size() > 0){
            List<Belong> belongList = new ArrayList<>();
            for(int num: groupsIdsList){
                belongList.add(new Belong(homework.getHomeworkId(), num));
            }
            System.out.println("belongList:"+belongList);
            //插入的行数
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
