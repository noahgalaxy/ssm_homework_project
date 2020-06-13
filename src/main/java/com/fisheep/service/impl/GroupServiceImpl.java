package com.fisheep.service.impl;

import com.fisheep.bean.Group;
import com.fisheep.dao.GroupMapper;
import com.fisheep.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupMapper groupMapper;
    @Override
    public List<Group> selectGroupsByGroupIdsList(List<Integer> groupsIdsList) {
        List<Group> groupList= groupMapper.selectGroupsByGroupIdsList(groupsIdsList);
        return groupList;
    }
}
