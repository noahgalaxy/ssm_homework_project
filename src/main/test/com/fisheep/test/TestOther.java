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
        String s = "交叉旋转机器的智能故障诊断技术在理论和工程上都具有重要意义。为此，本文提出了一种使用通过粒子群优化（PSO）优化的新型堆叠传输自动编码器（NSTAE）的新方法。 首先，设计了具有比例指数线性单位（SELU），熵和非负约束的新型堆叠自动编码器（NSAE）模型。 然后，使用NSAE和参数传递策略构造NSTAE，以使预训练的源域NSAE能够适应目标域样本。 最后，使用PSO灵活地确定NSTAE的超参数。 通过分析不同旋转机械轴承和齿轮的实验数据，研究了该方法的有效性和优越性。";

        s = s.replaceAll(" ", "");
        System.out.println(s);
    }
}
