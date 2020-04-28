package com.fisheep.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class UploadFile {
    public static Boolean fileSave(MultipartFile file , String path, String fileName){
        File pathFile = new File(path);
        if(!pathFile.exists()){
            pathFile.mkdir();
        }
        File myFile = new File(pathFile, fileName);
        System.out.println("myFile地址："+myFile.getAbsolutePath());
        try {
//            FileInputStream in = new FileInputStream((File)file); //这样会报错，MultipartFile不能转换成File类型
            InputStream in = file.getInputStream();
            FileOutputStream out = new FileOutputStream(myFile);
            int len = 0;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) {
                out.write(b);
            }
            out.flush();
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String getFileSavePath(){
        String path = "C:\\Users\\Fisheep\\Desktop\\Code\\homework\\src\\main\\webapp\\uploadfiles";
        return path;
    }
}
