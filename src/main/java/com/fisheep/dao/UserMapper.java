package com.fisheep.dao;

import com.fisheep.bean.User;


public interface UserMapper {

    public int insertUser(User user);

    int getEmailCounts(String email);

    User getUserByEmail(String email);
}
