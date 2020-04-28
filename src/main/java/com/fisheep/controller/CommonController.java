package com.fisheep.controller;

import com.fisheep.utils.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class CommonController {

//    再session里面获取uid
    @RequestMapping(path = "/getUidBySession")
    @ResponseBody
    public Msg getUidBySession(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        System.out.println("控制端 session："+session.toString() +"\n"+(Integer)session.getAttribute("uid")+
                "\nsessionID"+session.getId());
        if(session.isNew()){
            System.out.println("getUidBySession fail");
            return Msg.fail();
        }
        System.out.println("getUidBySession success");
        return Msg.success().add("uid",session.getAttribute("uid"));
    }
}
