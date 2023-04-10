package com.fuchuang.facerecognition;


import org.junit.Test;


public class StringTest {

    @Test
    public  void Test(){
        String xx = "8.134.108.219:8080/static/hello.world";
        String c = xx.substring(xx.lastIndexOf('/')+1,xx.length());

        System.out.println(c);
        System.out.println(xx.lastIndexOf('/')+1);

    }

}
