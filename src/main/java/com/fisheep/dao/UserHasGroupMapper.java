package com.fisheep.dao;

import com.fisheep.bean.UserHasGroup;

import java.util.List;
import java.util.Map;

public interface UserHasGroupMapper {
//    List<Map<Integer, String>> getGroupByUid(Integer uid);

    List<UserHasGroup> getGroupsByUid(Integer uid);
}
