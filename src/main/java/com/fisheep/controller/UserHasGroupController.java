package com.fisheep.controller;

import com.fisheep.bean.UserHasGroup;
import com.fisheep.service.UserHasGroupService;
import com.fisheep.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        List<UserHasGroup> uhgGroups = userHasGroupServiceImpl.getGroupsByUid(uid);
        if(!uhgGroups.isEmpty()){
            //map存放查询到的这个uid所有的组
            List<Map<String, Object>> groupsList = new ArrayList<>();
            for(UserHasGroup uhgGroup: uhgGroups){
                Map<String, Object> map = new HashMap<>();
                map.put("groupId",uhgGroup.getUhgGroupId());
                map.put("groupName", uhgGroup.getGroup().getGroupName());
                groupsList.add(map);
            }
            return Msg.success().add("groupsList", groupsList);
        }
        //这种情况是代表没有所属组
        return Msg.fail();
    }
}
