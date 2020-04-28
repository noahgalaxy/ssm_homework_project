package com.fisheep.controller;


import com.fisheep.bean.User;
import com.fisheep.service.UserService;
import com.fisheep.service.impl.UserServiceImpl;
import com.fisheep.utils.Msg;
import com.fisheep.utils.UploadFile;
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
        System.out.println("用户登陆："+user);
        User user1 = userServiceImpl.userSignUp(user);
        System.out.println("flag:"+user1);
        if(null != user1){
            HttpSession session = request.getSession();
            session.setAttribute("uid", user1.getUid());
            System.out.println("登陆的session："+session.toString() +"\n"+(Integer)session.getAttribute("uid")+
                    "\nsessionID"+session.getId());
            return Msg.success();
        }
        return Msg.fail();
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
        int rowsAffected = userServiceImpl.insertUser(user);
        System.out.println("rowsAffected: "+rowsAffected);
        if(rowsAffected > 0){
            return Msg.success().add("rowsAffected",rowsAffected);
        }else{
            return Msg.fail().add("rowsAffected",rowsAffected);
        }
    }


    @RequestMapping(path = "/fileUpload")
    @ResponseBody
    public Msg fileUpload( @RequestParam MultipartFile uploadFile){
        System.out.println("上传成功！！");
        System.out.println(uploadFile);
        //保存图像
        String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
        String absolutePath = UploadFile.getFileSavePath();
        System.out.println("absolutePath:"+absolutePath+"\t"+"fileName:"+fileName);

        if(UploadFile.fileSave(uploadFile, absolutePath, fileName)){
            return Msg.success();
        }
        return Msg.fail();
    }
}
