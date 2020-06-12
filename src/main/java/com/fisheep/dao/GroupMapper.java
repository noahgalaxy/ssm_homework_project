package com.fisheep.dao;

import com.fisheep.bean.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper {
    List<Group> getAllGroups();

    List<Group> selectGroupsByGroupIdsList(@Param("groupsIdsList") List<Integer> groupsIdsList);
}
