package com.fisheep.service;

import com.fisheep.bean.Group;

import java.util.List;

public interface GroupService {

    List<Group> selectGroupsByGroupIdsList(List<Integer> groupsIdsList);
}
