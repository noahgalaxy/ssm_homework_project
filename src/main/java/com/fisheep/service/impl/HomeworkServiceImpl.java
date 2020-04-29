package com.fisheep.service.impl;

import com.fisheep.bean.Homework;
import com.fisheep.dao.HomeworkMapper;
import com.fisheep.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeworkServiceImpl implements HomeworkService {
    @Autowired
    HomeworkMapper homeworkMapper;

    @Override
    public int insertHomework(Homework homework) {
        int rowsAffected = homeworkMapper.insertHomework(homework);
        return rowsAffected;
    }
}
