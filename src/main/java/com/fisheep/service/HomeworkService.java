package com.fisheep.service;

import com.fisheep.bean.Homework;

import java.util.List;

public interface HomeworkService {

    int insertHomework(Homework homework);

    List<Homework> getHomeworksWithGroupsByUid(int uid);

    int deleteHomeworkById(int homeworkId);

    int deleteHomeworkByBatchId(List<Integer> idList);

    Homework getHomeworkByHomeId(Integer homeworkId);

    int updateHomework(Homework homework);

    Boolean gethomeworkExpiredByHomeCode(String code);

    Homework getHomeworkByHomeworkCode(String code);

    String getHomeworkNameById(int homeworkid);
}
