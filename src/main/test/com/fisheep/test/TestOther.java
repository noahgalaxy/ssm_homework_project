package com.fisheep.test;

import org.junit.Test;

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
        String s ="1993年8月日本政府制定 “道 路 技 术 五 年 规 划”，明确指出大力支持研发ETC技术及推进其应 用。1997年，编制了有关电子不停 车收费标准草 案，并提交给国际标准化组织第204委 员 会。1999年3月制定 出 了ETC相关设备的技术规格标准。 2001年3月起实施全国大规模的ETC网络建设。 目前，日本已经建立起了全世界最大规模的联 网电子不停车收费系统，在全国范围内的所有高速 公路收费站点开通了ETC系统，收费站点总数超过2000个，用 户 数 量 达4000万 辆。ETC车 道 的 利 用率已经达到８６％。";



        s = s.replaceAll(" ", "");
        System.out.println(s);
    }
}
