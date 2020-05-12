package com.fisheep.service.impl;

import com.fisheep.bean.Homework;
import com.fisheep.dao.HomeworkMapper;
import com.fisheep.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkServiceImpl implements HomeworkService {
    @Autowired
    HomeworkMapper homeworkMapper;

    @Override
    public int insertHomework(Homework homework) {
        int lastInsertId = homeworkMapper.insertHomework(homework);
        return lastInsertId;
    }

    @Override
    public List<Homework> getHomeworksWithGroupsByUid(int uid) {
        List<Homework> homeworkList = homeworkMapper.getHomeworksWithGroupsByUid(uid);
        return homeworkList;
    }

    @Override
    public int deleteHomeworkById(int homeworkId) {
        return homeworkMapper.deleteHomeworkById(homeworkId);
    }

    @Override
    public int deleteHomeworkByBatchId(List<Integer> idList) {
        return homeworkMapper.deleteHomeworkByBatchId(idList);
    }


}
