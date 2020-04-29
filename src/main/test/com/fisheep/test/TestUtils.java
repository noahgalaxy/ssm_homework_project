package com.fisheep.test;

import com.fisheep.utils.SnowAlgorithum;
import org.junit.Test;

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

}
