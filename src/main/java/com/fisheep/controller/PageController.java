package com.fisheep.controller;


import com.fisheep.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PageController {
    private static Logger logger = LogManager.getLogger(PageController.class.getName());

    @Autowired
    RedisService redisServiceImpl;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping(path = "/toRelease")
    public String toRelease(){
//        System.out.println("页面跳转： release");
        logger.info("页面跳转： release");
        return "release";
    }

    @RequestMapping(path = "/toLogin")
    public ModelAndView toLogin(){
//        String page = "login";
//        System.out.println("页面跳转： toLogin");
        logger.info("页面跳转： toLogin");
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }
//    @RequestMapping(path = "/toLogin")
//    public String toLogin(){
//        System.out.println("页面跳转： toLogin");
//        return "login";
//    }

    @RequestMapping(path = "/toGroup")
    public String toGroup(){
//        System.out.println("页面跳转： groups");
        logger.info("页面跳转： groups");
        return "groups";
    }

//    @RequestMapping(path = "/singlehomework/{homeworkId}", method = RequestMethod.GET)
//    public ModelAndView toSingleHomework(@PathVariable("homeworkId") int homeworkId){
//        ModelMap model = new ModelMap();
//        model.addAttribute("homeworkId", homeworkId);
//        model.addAttribute("name","yuyang");
//        return new ModelAndView("single-homework",model);
//    }
//    @RequestMapping(path = "/singlehomework/{homeworkId}")
//    public String toSingleHomework(HttpServletRequest re, HttpServletResponse resp,
//                                   @PathVariable("homeworkId") String homeworkId){
////        return "forward:single-homework?homeworkId="+homeworkId;
////        return "forward:/WEB-INF/views/single-homework.html";
//        //请求转发url不变
//        return "forward:/WEB-INF/views/single-homework.html?homeworkId="+homeworkId;
////        return "single-homework.html";
//    }

    @RequestMapping(path = "/singlehomework/{homeworkId}")
    public ModelAndView toSingleHomework(HttpServletRequest re, HttpServletResponse resp,
                                   @PathVariable("homeworkId") int homeworkId){
//        return "forward:single-homework?homeworkId="+homeworkId;
//        return "forward:/WEB-INF/views/single-homework.html";
        //请求转发url不变
        ModelAndView model = new ModelAndView("single-homework");
        model.addObject("homeworkId", homeworkId);
//        return "forward:/WEB-INF/views/single-homework.html?homeworkId="+homeworkId;
        logger.info("页面跳转： /singlehomework/"+homeworkId);
        return model;
//        return "single-homework.html";
    }


    @RequestMapping(path = "/toHomeworkSubmit/{code}")
    public String toHomeworkSubmit(@PathVariable("code") String homeworkCode, HttpServletResponse response){
        logger.info("进入toHomeworkSubmit/"+homeworkCode);
        Jedis jedis = jedisPool.getResource();
        Boolean exists = jedis.exists("code_id:" + homeworkCode);
        logger.info(homeworkCode+"是否过期（redis缓存中是否存在）？"+exists.toString());
        if(exists){
            return "forward:/WEB-INF/views/homeworksubmit.html";
        }
//        return "redirect:/index.html";
        return "expiredPage";
    }

    @RequestMapping(path = "/toIndex")
    public String toIndex(){
        return "redirect:/index.html";
    }
}
