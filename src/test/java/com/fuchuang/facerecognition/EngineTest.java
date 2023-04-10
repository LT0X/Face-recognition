package com.fuchuang.facerecognition;

import com.arcsoft.face.FaceEngine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {FaceRecognitionApplication.class})
public class EngineTest {

    @Autowired
    @Qualifier("VideoArcFace")
    private FaceEngine videoEngine;

    @Autowired
    @Qualifier("ImageArcFace")
    private FaceEngine imagEngine;

    @Test
    public  void TestEngine(){
        System.out.println(videoEngine);
//        System.out.println(imagEngine);
    }


}
