package com.fisheep.test;

import com.fisheep.bean.Homework;
import com.sun.javafx.scene.layout.region.SliceSequenceConverter;
import org.junit.Test;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestOther {
    private Date date;

    @Test
    public void testDate(){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS" );
        date = new Date();
        String s = sdf.format(date);
        System.out.println(s);
    }

    @Test
    public void testSplit(){
        String s = "";
        System.out.println("长度："+s.length());
        String[] ss = {"asd", "ass"};
        char[] a = s.toCharArray();
        System.out.println("s阿斯蒂:"+(a[0]- '0'));

        for(char g: a){
            System.out.println("+===="+g);
        }
        System.out.println(ss.toString());
//        System.out.println(a);
    }

    @Test
    public void testGetAll(){
//        String s = "";
//
//        s = s.replaceAll(" ", "");
//        System.out.println(s);
        String s = null;
        System.out.println(null != s);

    }

    @Test
    public void testGetAttributrByReflict(){
//        List<Integer> nums = new ArrayList<>();
        List<Integer> nums = null;
        System.out.println(nums.isEmpty() || nums == null);
    }

    @Test
    public void testSubstring(){
        String[] s = {"4cfa3aed-b81d-4bee-8068-357121704eb2_专业英语 - 论文.docx",
        "df224e5e-737d-4d95-895b-fcea7482e5f7_基于非平稳信号组合分析的故障诊断方法_陈哲.pdf"};
        for (String s1 : s) {
            System.out.println(s1.substring(36));
        }
    }
    @Test
    public void testGetPath(){
        File file = new File("");
        try {
            String canonicalPath = file.getCanonicalPath();
            System.out.println(canonicalPath);
            System.out.println(File.separator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
