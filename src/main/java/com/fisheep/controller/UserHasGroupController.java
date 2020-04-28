package com.fisheep.controller;

import com.fisheep.service.UserHasGroupService;
import com.fisheep.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class UserHasGroupController {

    @Autowired
    @Qualifier("userHasGroupServiceImpl")
    UserHasGroupService userHasGroupServiceImpl;

    @RequestMapping(path = "/getGroupByUid/{uid}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getGroupByUid(@PathVariable("uid") Integer uid){
        System.out.println("getGroupByUid里面uid:"+uid);
        Map<Integer, String> groupsMap = userHasGroupServiceImpl.getGroupByUid(uid);
        if(null != groupsMap){
            return Msg.success().add("groupsMap", groupsMap);
        }
        //这种情况是代表没有所属组
        return Msg.fail();
    }
}
