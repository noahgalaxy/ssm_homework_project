package com.fisheep.service.impl;

import com.fisheep.bean.User;
import com.fisheep.dao.UserMapper;
import com.fisheep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insertUser(User user) {
        int rowsAffected = userMapper.insertUser(user);
        System.out.println("service层新插入的id："+user.getUid());
        return rowsAffected;
    }

    @Override
    public boolean checkEmail(String email) {
        int counts = userMapper.getEmailCounts(email);
        System.out.println("counts数量："+counts);
        if(counts > 0){
            return false;
        }
        return true;
    }

    @Override
    public User userSignUp(User user) {

        User user1 = userMapper.getUserByEmail(user.getEmail());
        System.out.println("传进来的user:"+user);
        System.out.println("数据库查询到的user:"+user1);

        return user1;
    }
}
