package com.fisheep.controller;

import com.fisheep.utils.Msg;
import com.fisheep.utils.SnowAlgorithum;
import com.fisheep.utils.UploadFile;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.runners.Parameterized;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class CommonController {

//    再session里面获取uid
    @RequestMapping(path = "/getUidBySession")
    @ResponseBody
    public Msg getUidBySession(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        System.out.println("控制端 session："+session.toString() +"\n"+(Integer)session.getAttribute("uid")+
                "\nsessionID"+session.getId());
        if(session.isNew()){
            System.out.println("getUidBySession fail");
            return Msg.fail();
        }
        System.out.println("getUidBySession success");
        return Msg.success().add("uid",session.getAttribute("uid"));
    }

//    @RequestMapping(path = "/fileUpload", method = RequestMethod.POST)
//    @ResponseBody
//    public Msg fileUpload(MultipartFile multipartFile,
//                          @RequestParam("uploaderName") String uploaderName,
//                          @RequestParam("homeworkId") int homeworkId,
//                          @RequestParam("homeworkCode") String homeworkCode,
//                          @RequestParam("location") String location){
//        //1.获取上传文件真实名字
//        String originalFilename = multipartFile.getOriginalFilename();
//        long size = multipartFile.getSize();
//        System.out.println("上传文件true filename："+originalFilename +"\nsize："+size/1024 +"M");
//        String newFIleName = homeworkId+homeworkCode+"_"+originalFilename;
//        String Dir = "";
//        System.out.println("newFIleName: "+newFIleName);
////
////        File file = new File(Dir+newFIleName);
////        try {
////            multipartFile.transferTo(file);
////        } catch (IOException e) {
////            e.printStackTrace();
////            return Msg.fail();
////        }
//        return Msg.success();
//    }

    @RequestMapping(path = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public Msg fileUpload1(@RequestParam("input-b2") CommonsMultipartFile multipartFile,
                           @RequestParam("uploaderName") String uploaderName,
                           @RequestParam("homeworkId") int homeworkId,
                           @RequestParam("homeworkCode") String homeworkCode,
                           @RequestParam("location") String location) {
        if (multipartFile.isEmpty()) {
            System.out.println("上传文件为空");
            return Msg.fail();
        }
        //1.获取上传文件真实名字
        String originalFilename = multipartFile.getOriginalFilename();
        long size = multipartFile.getSize();
        System.out.println("上传文件true filename：" + originalFilename + "\nsize：" + size / 1024. / 1024. + "M");
        System.out.println("uploaderName" + uploaderName);
        System.out.println("homeworkId  " + homeworkId + "\thomeworkCode  " + homeworkCode);
        System.out.println("location:" + location);
//        String code = SnowAlgorithum.getCode();
        String fileName = homeworkId+"_"+uploaderName+"_"+originalFilename;
        Boolean fileSave = UploadFile.fileSave(multipartFile, UploadFile.getFileSavePath(), fileName);
        if(fileSave){
            return Msg.success();
        }
        return Msg.fail();
    }
}
