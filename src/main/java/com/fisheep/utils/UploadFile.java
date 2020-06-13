package com.fisheep.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class UploadFile {

    /**
     * 对上传的文件进行存储
     * @param file：上传的文件commonMultipartFile类型
     * @param path：要粗存到哪里，哪个目录
     * @param fileName：目录下的文件名字
     * @return
     */
    public static Boolean fileSave(MultipartFile file , String path, String fileName){
        File pathFile = new File(path);
        if(!pathFile.exists()){
            pathFile.mkdir();
        }
        //在这个目录下面建立文件fileName
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
            //刷新缓冲区
            out.flush();
            //关闭输入流
            in.close();
            //关闭输出流
            out.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
//            e.printStackTrace();
//            return false;
        }
    }
    public static String getFileSavePath(){
        String path = "C:\\Users\\Fisheep\\Desktop\\Code\\homework\\src\\main\\webapp\\uploadfiles";
        return path;
    }

    /**
     * 删除文件，文件不存在或者传入的是一个目录，则返回true，删除成功返回true，否则返回false；
     * @param filePath：待删除的文件
     * @return
     */
    public static boolean deleteFile(String filePath){
        System.out.println("文件删除路径："+filePath);
        File file = new File(filePath);

        if(!file.exists() || file.isDirectory()){
            System.out.println("文件不存在或者是个目录");
            return true;
        }
        return file.delete();
    }
}
