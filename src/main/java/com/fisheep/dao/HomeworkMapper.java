package com.fisheep.dao;

import com.fisheep.bean.Homework;

import java.util.List;

public interface HomeworkMapper {
    int insertHomework(Homework homework);

    List<Homework> getHomeworksWithGroupsByUid(int uid);

    List<Homework> getAllHomeworksWithGroups();

    int deleteHomeworkById(int homeworkId);

    int deleteHomeworkByBatchId(List<Integer> idList);

    Homework getHomeworkByHomeId(Integer homeworkId);

    int updateHomework(Homework homework);

    Boolean gethomeworkExpiredByHomeCode(String code);

    Homework getHomeworkByHomeworkCode(String code);

    void updateHomeworkSubmittedNumsPlus(int submitHomeworkId);

    List<Homework> getAllHomeworks();

    String getHomeworkNameById(int homeworkid);
}
