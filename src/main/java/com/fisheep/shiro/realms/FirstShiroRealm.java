package com.fisheep.shiro.realms;

import com.fisheep.bean.User;
import com.fisheep.controller.CommonController;
import com.fisheep.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FirstShiroRealm extends AuthorizingRealm {

    private static Logger logger = LogManager.getLogger(FirstShiroRealm.class.getName());
    @Autowired
    @Qualifier("userService")
    private UserService userServiceImpl;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        System.out.println("执行了鉴权");
        logger.info("执行了鉴权");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        System.out.println("执行了认证");
        logger.info("执行了认证");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        User user = new User();
        user.setEmail(usernamePasswordToken.getUsername());
        User user1 = userServiceImpl.userSignUp(user);
        logger.info("登陆认证：数据库查到的user："+user.toString());
        if(!user1.getEmail().equals(usernamePasswordToken.getUsername())){
            logger.info("认证失败，用户名错误");
            return null;
        }
        return new SimpleAuthenticationInfo(user1,user1.getPassWord(),"");
    }
}
