package com.fisheep.test;

import com.fisheep.bean.Homework;
import com.fisheep.utils.SnowAlgorithum;
import com.fisheep.utils.StringToNum;
import com.fisheep.utils.UploadFile;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUtils {


    @Test
    public void testUuid(){
        for (int i = 0; i< 6; i++) {
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
            System.out.println(uuid.replaceAll("-", ""));
        }
    }

    @Test
    public void testGetCode(){
        for(int i = 0; i< 10; i++){
            String s = SnowAlgorithum.getCode();
            System.out.println(s);

        }
    }

    @Test
    public void testStringToNum(){
//        String[] s = {"-","2-","10-4-5-5","22-0-100-2-"};
        String[] s = {"-"};
        for(String ss: s){
//            for(String sss:ss.split("-")){
//                System.out.print(sss);
//                if("" ==sss){
//                    System.out.print("错过");
//                    continue;
//                }
////                System.out.print("解析："+Integer.parseInt(sss)+"\t");
            List<Integer> o = StringToNum.numStringToSingleNum(ss);
            System.out.print(o+"\t");
            System.out.println("size："+o.size());
            if(null != o){
                System.out.print("比较："+o.size());

            }
            System.out.println();
            System.out.println("=======");
            }

//            System.out.println(StringToNum.numStringToSingleNum(ss));
    }
    @Test
    public void test1(){
        Homework ho = new Homework();
        ho.setGroupsIdString("-");
        System.out.println(ho.getGroupsIdString());
        System.out.println(StringToNum.numStringToSingleNum(ho.getGroupsIdString()));
    }

    @Test
    public void testFileDelete(){
        String filePath = "C:\\Users\\Fisheep\\Desktop\\Code\\homework\\src\\main\\webapp\\uploadfiles\\0beb3370-338a-46cb-aef9-bcee3063143e_抗拼接攻击的矢量瓦片数据水印算法_唐伟.pdf";
        boolean isDelete = UploadFile.deleteFile(filePath);
        System.out.println("isDelete: "+isDelete);
    }
}
