package com.fisheep.service;

import com.fisheep.bean.User;


public interface UserService {
    public int insertUser(User user);

    boolean checkEmail(String email);

    User userSignUp(User user);
}
