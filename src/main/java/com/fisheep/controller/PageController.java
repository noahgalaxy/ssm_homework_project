package com.fisheep.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
}
