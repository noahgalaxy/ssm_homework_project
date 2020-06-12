package com.fisheep.service;


import com.fisheep.bean.Homework;

import java.util.List;

public interface RedisService {


    Boolean getExpired(String homeworkCode);

    List<Homework> getHomeworksWithGroupsByUid(int uid) throws IllegalAccessException;

    Homework getHomeworkByHomeId(Integer homeworkId);

    void insertHomework(Homework homework);

    void insertCodeIdExpire(String homeworkCode, int homeworkId, String homeworkDead);
}
