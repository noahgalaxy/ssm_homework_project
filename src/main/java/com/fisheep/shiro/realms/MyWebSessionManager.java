package com.fisheep.shiro.realms;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

public class MyWebSessionManager extends DefaultWebSessionManager {
    /*
    这里继承DefaultWebSessionManager然后重写这个方法返回true，表示shiro的session和httpsession为同一个session；
    然后需要在springxml配置shiro的地方将sessionManager改变为这各manager
    */
    @Override
    public boolean isServletContainerSessions() {
        return true;
    }
}
