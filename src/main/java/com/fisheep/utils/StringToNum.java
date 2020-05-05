package com.fisheep.utils;

import java.util.ArrayList;
import java.util.List;

public class StringToNum {
    public static List numStringToSingleNum(String stringNum){
        if(stringNum == "" || stringNum == "-"){return null;}
        List<Integer> numsList = new ArrayList<>();
        for(String s : stringNum.split("-")){
            if(null != s){
                numsList.add(Integer.parseInt(s));
            }
        }
        return numsList;
    }
}
