package com.fisheep.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PageController {
    @RequestMapping(path = "/toRelease")
    public String toRelease(){
        System.out.println("页面跳转： release");
        return "release";
    }

    @RequestMapping(path = "/toLogin")
    public ModelAndView toLogin(){
//        String page = "login";
//        System.out.println("页面跳转： toLogin");
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
        System.out.println("页面跳转： groups");
        return "groups";
    }

//    @RequestMapping(path = "/singlehomework/{homeworkId}", method = RequestMethod.GET)
//    public ModelAndView toSingleHomework(@PathVariable("homeworkId") int homeworkId){
//        ModelMap model = new ModelMap();
//        model.addAttribute("homeworkId", homeworkId);
//        model.addAttribute("name","yuyang");
//        return new ModelAndView("single-homework",model);
//    }
    @RequestMapping(path = "/singlehomework/{homeworkId}")
    public String toSingleHomework(HttpServletRequest re, HttpServletResponse resp,
                                   @PathVariable("homeworkId") String homeworkId){
//        return "forward:single-homework?homeworkId="+homeworkId;
//        return "forward:/WEB-INF/views/single-homework.html";
        return "forward:/WEB-INF/views/single-homework.html?homeworkId="+homeworkId;
//        return "single-homework.html";
    }
}
