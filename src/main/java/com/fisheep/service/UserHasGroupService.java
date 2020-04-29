package com.fisheep.service;

import com.fisheep.bean.UserHasGroup;

import java.util.List;
import java.util.Map;

public interface UserHasGroupService {

    /**
     * 返回这个uid所在的group的groupId和groupName组成的map
     * @param uid
     * @return
     */
//    Map<Integer, String> getGroupByUid(Integer uid);

    List<UserHasGroup> getGroupsByUid(Integer uid);
}
