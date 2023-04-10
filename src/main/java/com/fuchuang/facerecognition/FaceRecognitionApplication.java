package com.fuchuang.facerecognition;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;



@MapperScan("com/fuchuang/facerecognition/dao")
@ServletComponentScan(basePackages = "com.fuchuang.facerecognition")
@SpringBootApplication()
public class FaceRecognitionApplication {

    public static void main(String[] args) {

        SpringApplication.run(FaceRecognitionApplication.class, args);

     }

}
