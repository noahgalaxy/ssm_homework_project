package com.fisheep.service.impl;

import com.fisheep.bean.UserHasGroup;
import com.fisheep.dao.UserHasGroupMapper;
import com.fisheep.service.UserHasGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "userHasGroupServiceImpl")
public class UserHasGroupServiceImpl implements UserHasGroupService {
    @Autowired
    UserHasGroupMapper userHasGroupMapper;

//    @Override
//    public Map<Integer, String> getGroupByUid(Integer uid) {
//        List<Map<Integer, String>> groups = userHasGroupMapper.getGroupByUid(uid);
//        if(groups.size() > 0){
//            Map<Integer, String> groupsMap = new HashMap<Integer, String>();
//            for(Map<Integer, String> map: groups){
//                for(Integer key: map.keySet()){
//                    groupsMap.put(key, map.get(key));
//                }
//            }
//            return groupsMap;
//        }
//
//        return null;
//    }

    @Override
    public List<UserHasGroup> getGroupsByUid(Integer uid) {

        return userHasGroupMapper.getGroupsByUid(uid);
    }
}
