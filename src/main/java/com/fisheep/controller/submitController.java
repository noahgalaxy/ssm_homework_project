package com.fisheep.controller;


import com.fisheep.bean.Homework;
import com.fisheep.bean.Submit;
import com.fisheep.service.HomeworkService;
import com.fisheep.service.SubmitService;
import com.fisheep.utils.Msg;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class submitController {
    @Autowired
    HomeworkService homeworkServiceImpl;

    @Autowired
    SubmitService submitServiceImpl;

    /**
     * 失败返回info的值：
     *          0：空输入，
     *          1：码不存在
     *          2:作业已过期
     * @param code
     * @return
     */
    @RequestMapping(path = "/homeworkCode/{code}", method = RequestMethod.GET)
    @ResponseBody
    public Msg code(@PathVariable("code") String code) {
        //去除码中的所有空格
        code = code.replaceAll(" ","");

        if (code == "") {
            return Msg.fail().add("info", 0);
        }
        Boolean expired = homeworkServiceImpl.gethomeworkExpiredByHomeCode(code);
        /*
        1.不存在，返回1
        2.作业过期--》返回2
         */
        if (null == expired) {
            return Msg.fail().add("info", 1);
        }
        if (expired){
            return Msg.fail().add("info", 2);
        }
        return Msg.success();
    }

    /**
     *
     * 这里不需要判断，因为是请求转发过来立马就夹在，前面页面是进行判断了才过来的
     * 失败返回info的值：
     *          0：空输入，
     *          1：码不存在
     *          2:作业已过期
     * @param code
     * @return
     */
    @RequestMapping(path = "/homeworkCode/{code}", method = RequestMethod.POST)
    @ResponseBody
    public Msg getHomeworkByHomeworkCode(@PathVariable("code") String code) {
//        //去除码中的所有空格
//        code = code.replaceAll(" ","");
//
//        if (code == "") {
//            return Msg.fail().add("info", 0);
//        }
        Homework homework = homeworkServiceImpl.getHomeworkByHomeworkCode(code);
//        /*
//        1.不存在，返回1
//        2.作业过期--》返回2
//         */
//        if (null == homework) {
//            return Msg.fail().add("info", 1);
//        }
//        if (homework.isExpired()){
//            return Msg.fail().add("info", 2);
//        }
        return Msg.success().add("homework", homework);
    }

    @RequestMapping(path = {"submit/{submitHomeworkId}", "singlehomework/submit/{submitHomeworkId}"}, method = RequestMethod.GET)
    @ResponseBody
    public Msg getSubmitedHomeworksByhomeworkId(@PathVariable("submitHomeworkId") int submitHomeworkId){
        List<Submit> submits = submitServiceImpl.selectAllByHomeworkId(submitHomeworkId);
        return Msg.success().add("submits", submits);
    }
}
