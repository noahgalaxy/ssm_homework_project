package com.fisheep.utils;

import java.util.ArrayList;
import java.util.List;

public class StringToNum {
    public static List numStringToSingleNum(String stringNum){
        if(stringNum == "" || stringNum == "-"){
            System.out.println("返回null");
            return null;
        }
        List<Integer> numsList = new ArrayList<>();
        for(String s : stringNum.split("-")){
            if(null != s){
                numsList.add(Integer.parseInt(s));
            }
        }
        System.out.println("字符串处理之后list的长度："+numsList.size());
        return numsList;
    }
}
