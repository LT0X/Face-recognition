package com.fuchuang.facerecognition;


import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.toolkit.ImageInfoEx;
import com.fuchuang.facerecognition.Init.UserList;
import com.fuchuang.facerecognition.dao.UserDao;
import com.fuchuang.facerecognition.model.User;
import com.fuchuang.facerecognition.model.UserBean;
import com.fuchuang.facerecognition.utils.ArcfaceWorker;
import com.fuchuang.facerecognition.utils.ImageUtils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = {FaceRecognitionApplication.class})
public class UserLoginTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserList userList;

    @Autowired
    private  ArcfaceWorker arcfaceWorker;


    @Test
    public void InsertUser() throws IOException {
        File file = new File("D:\\projectImg\\test1.jpg");
        byte[] fileByte = Files.readAllBytes(file.toPath());
        //将传入的数据转为图片信息
        ImageInfoEx imageInfoEx = ImageUtils.GetImageInfoEx(fileByte);

        //获得人脸信息List
        List<FaceInfo> faceInfosMe = arcfaceWorker
                .detectFace(imageInfoEx);
        //获得人脸信息特点
        FaceFeature faceFeatureMe = arcfaceWorker
                .extractFaceFeature(faceInfosMe,imageInfoEx);

        User user = new User("xiaoli",faceFeatureMe.getFeatureData());

        //插入到数据库
        int judge = userDao.AddUser(user);
        if (judge == 0){
            System.out.println("插入失败");
        }

    }

    @Test
    public void GetUserList() {

        Map<Integer,UserBean> map = userList.getMap();

        return ;
    }


}
