package com.fisheep.controller;


import com.fisheep.bean.User;
import com.fisheep.service.UserService;
import com.fisheep.service.impl.UserServiceImpl;
import com.fisheep.utils.Msg;
import com.fisheep.utils.UploadFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static Logger logger = LogManager.getLogger(UserController.class.getName());


    @Autowired
    @Qualifier("userService")
    private UserService userServiceImpl;


    @RequestMapping(path = "/checkEmail")
    @ResponseBody
    public Msg checkEmail(@RequestParam("email") String email){
        boolean flag = userServiceImpl.checkEmail(email);
        if(flag){
            return Msg.success();
        }
        return Msg.fail();
    }
    /**
     * 处理用户登陆
     * @param user
     * @return
     */
    @RequestMapping(path = "/userSignUp")
    @ResponseBody
    public Msg userSignUp(User user, HttpServletRequest request){
        logger.info("用户登陆："+user.toString());
        System.out.println("用户登陆："+user);
        UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassWord());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);

            User principal = (User) subject.getPrincipal();
            //
            subject.getSession().setTimeout(1000*60*60*24*30);
            logger.info("session过期时间：{}"+subject.getSession().getTimeout());
            System.out.println("session过期时间："+subject.getSession().getTimeout());
            System.out.println("subject.getPrincipal字符串："+subject.getPrincipal().toString());
            HttpSession session = request.getSession();
            session.setAttribute("uid", principal.getUid());
            System.out.println("登陆的session："+session.toString() +"\nsession里面放进去的uid："+(Integer)session.getAttribute("uid")+
                    "\nsessionID-->"+session.getId());
            return Msg.success();
        }catch (UnknownAccountException e){
            logger.info("无此用户名");
            System.out.println("无此用户名邮箱："+user.getEmail());
            return Msg.fail();
        }catch (IncorrectCredentialsException e){
            System.out.println("密码不正确");
            logger.info("密码不正确:"+user.getPassWord());
            return Msg.fail();
        }
//        if(null != user1 ){
//            HttpSession session = request.getSession();
//            session.setAttribute("uid", user1.getUid());
//            System.out.println("登陆的session："+session.toString() +"\n"+(Integer)session.getAttribute("uid")+
//                    "\nsessionID"+session.getId());
//            return Msg.success();
//        }
//        return Msg.fail();
    }

    /**
     * 处理用户注册
     * @param user
     * @return
     */
    @RequestMapping(path = "/userSignIn")
    @ResponseBody
    public Msg userSignIn(User user){
        System.out.println("用户注册："+user);
        logger.info("用户注册："+user.toString());

        //密码和邮箱（用户名不能为空）
        if(user.getEmail() == null || user.getPassWord() == null){
            return Msg.fail();
        }
        if(user.getEmail().replaceAll(" ", "") == ""
                || user.getPassWord().replaceAll(" ","") == ""){
            return Msg.fail();
        }
        int rowsAffected = userServiceImpl.insertUser(user);
//        System.out.println("rowsAffected: "+rowsAffected);
        if(rowsAffected > 0){
            logger.info("注册成功："+user.toString());
            return Msg.success().add("rowsAffected",rowsAffected);
        }else{
            logger.info("注册失败："+user.toString());
            return Msg.fail().add("rowsAffected",rowsAffected);
        }
    }

    @RequestMapping(path = "/fileUpload")
    @ResponseBody
    public Msg fileUpload( @RequestParam MultipartFile uploadFile){
//        System.out.println("上传成功！！");
//        System.out.println(uploadFile);
        //保存图像
        String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
        String absolutePath = UploadFile.getFileSavePath();
//        System.out.println("absolutePath:"+absolutePath+"\t"+"fileName:"+fileName);
        logger.info("absolutePath:"+absolutePath+"\t"+"fileName:"+fileName);
        if(UploadFile.fileSave(uploadFile, absolutePath, fileName)){
            logger.info("文件保存成功");
            return Msg.success();
        }
        logger.info("文件保存失败");
        return Msg.fail();
    }
}
