package com.fisheep.test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestOther {
    private Date date;

    @Test
    public void testDate(){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS" );
        date = new Date();
        String s = sdf.format(date);
        System.out.println(s);
    }
}
