package com.fisheep.dao;

import com.fisheep.bean.Homework;

import java.util.List;

public interface HomeworkMapper {
    int insertHomework(Homework homework);

    List<Homework> getHomeworksWithGroupsByUid(int uid);

    int deleteHomeworkById(int homeworkId);

    int deleteHomeworkByBatchId(List<Integer> idList);
}
