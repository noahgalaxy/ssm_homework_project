package com.fisheep.service.impl;

import com.fisheep.bean.Belong;
import com.fisheep.dao.BelongMapper;
import com.fisheep.service.BelongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BelongServiceImpl implements BelongService {

    @Autowired
    BelongMapper belongMapper;

    @Override
    public int insertBelong(List<Belong> belongList) {
        System.out.println("BelongServiceImpl的insertBelong成功执行");
        return belongMapper.insertBelong(belongList);
    }
}
