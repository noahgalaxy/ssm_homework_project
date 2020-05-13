package com.fisheep.controller;

import com.fisheep.bean.Belong;
import com.fisheep.bean.Homework;
import com.fisheep.service.BelongService;
import com.fisheep.service.HomeworkAndBelongService;
import com.fisheep.service.HomeworkAndGroupService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
//@Transactional(rollbackFor = {Exception.class})
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;

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

    @RequestMapping(path = "/getHomeworksByUid", method = RequestMethod.POST)
    @ResponseBody
    public Msg getHomeworksByUid(HttpSession session){
        System.out.println("进入getHomeworksByUid");
//        从seession里面拿出uid
        int uid = (int) session.getAttribute("uid");
        List<Homework> homeworkList = homeworkService.getHomeworksWithGroupsByUid(uid);
        if(homeworkList.size() == 0){
            System.out.println("size为0");
            return Msg.fail();
        }
        List<Map<String, Object>> homeworks = new ArrayList<>();
        for(Homework homework: homeworkList){
            Map<String, Object> homeworkMap = new HashMap<>();
            homeworkMap.put("homeworkId",homework.getHomeworkId());
            homeworkMap.put("homeworkName", homework.getHomeworkName());
            homeworkMap.put("homeworkCode", homework.getHomeworkCode());
            homeworkMap.put("homeworkDead", homework.getHomeworkDead());
            homeworkMap.put("homeworktotalnums", homework.getHomeworktotalnums());
            homeworkMap.put("homeworksubmittednums", homework.getHomeworksubmittednums());
            homeworkMap.put("groups", homework.getGroups());
            homeworkMap.put("expired", homework.isExpired());
            homeworks.add(homeworkMap);
        }
        System.out.println("homeworks"+homeworks);
        return Msg.success().add("homeworks", homeworks);
    }

//    删除restful风格
    @RequestMapping(path = "/homework/{homeworkIds}", method = RequestMethod.DELETE)
    @ResponseBody()
    public Msg deleteHomeworkById(@PathVariable("homeworkIds") String homeworkIds){
        int rowsAffected = 0;
        System.out.println("要删除的作业ids："+homeworkIds);
        if(homeworkIds.contains("-")){
            String[] ids = homeworkIds.split("-");
            List<Integer> idList = new ArrayList<>();
            for (String id : ids) {
                idList.add(Integer.parseInt(id));
            }
            rowsAffected = homeworkService.deleteHomeworkByBatchId(idList);
        }else{
            int homeworkId = Integer.parseInt(homeworkIds);
            rowsAffected = homeworkService.deleteHomeworkById(homeworkId);
        }
        return rowsAffected == 0?Msg.fail():Msg.success();
    }

    //跟据homeworkId查询单个作业
    @RequestMapping(path = "/homework/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getHomeworkByHomeId(@PathVariable("id") Integer homeworkId){
        Homework homework = homeworkService.getHomeworkByHomeId(homeworkId);
        System.out.println("收到的homeworkId: "+homeworkId+"\ngetHomeworkByHomeId :"+homework);
        return homework == null ? Msg.fail():Msg.success().add("homework",homework);
    }

    @RequestMapping(path = "/homework", method = RequestMethod.PUT)
    @ResponseBody
    public Msg updateHomework(Homework homework, HttpSession session){
        System.out.println("  修改的作业：\n"+homework);
        System.out.println("groupsIdString:"+homework.getGroupsIdString() + "type:"+homework.getGroupsIdString().getClass());
        int uid;
        if(session.isNew()){
            System.out.println("session is new!! ");
            return Msg.fail();
        }
        //作业名字不能为空，截止日期不能为空，作业全部数量不能为空
        if(homework.getHomeworkName() == "" || homework.getHomeworkDead() == "" || homework.getHomeworktotalnums() == 0){
            System.out.println("修改字段空");
            return Msg.fail();
        }
        int updateRowsAffected = homeworkService.updateHomework(homework);
        return Msg.success();
    }
}
