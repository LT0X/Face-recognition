package com.fuchuang.facerecognition.service.impl;


import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.toolkit.ImageInfoEx;
import com.fuchuang.facerecognition.Init.UserList;
import com.fuchuang.facerecognition.dao.UserDao;
import com.fuchuang.facerecognition.dao.WarningDao;
import com.fuchuang.facerecognition.model.Response;
import com.fuchuang.facerecognition.model.User;
import com.fuchuang.facerecognition.model.UserBean;
import com.fuchuang.facerecognition.service.UserLoginService;
import com.fuchuang.facerecognition.utils.ArcfaceWorker;
import com.fuchuang.facerecognition.utils.ImageUtils;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {


    @Autowired
    private ArcfaceWorker arcfaceWorker;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WarningDao warningDao;

    @Autowired
    private UserList userList;

    @Value("${imagePath}")
    private String path;

    @Value("${serviceIP}")
    private String serviceIp;


    @Override
    public Response login(byte[] image) {

        Response  response  = new Response();

        //将传入的数据转为图片信息
        ImageInfoEx imageInfoEx = ImageUtils.GetImageInfoEx(image);

        //获得人脸信息List
        List<FaceInfo> faceInfosMe = arcfaceWorker
                .detectFace(imageInfoEx);

        int judge = arcfaceWorker.detectionRGB(imageInfoEx,faceInfosMe);

        if (judge < 0.5){
            //不是为活体，插入警告信息
            response.setMsg("检测到异常人脸识别行为，警告");
            response.setStatusCode(-1);
            warningDao.AddWarning("检测到异常人脸识别行为，警告");
            return response;
        }

        //获得人脸信息特点
        FaceFeature faceFeatureMe = arcfaceWorker
                .extractFaceFeature(faceInfosMe,imageInfoEx);

        //开始获取数据库的特征信息
        Map<Integer, UserBean> map = userList.getMap();


        //进行比对,
        for (UserBean user : map.values()){
            FaceSimilar faceSimilar = arcfaceWorker.
                    compareFaceFeature(faceFeatureMe.getFeatureData(), user.getFaceImage());
            if (faceSimilar.getScore() > 0.8){
                response.setMsg(user.getName());
                break;
            }
        }

        response.setStatusCode(0);
        return response;

    }

    @Override
    public Response register(UserBean userInfo) {

        Response  response  = new Response();
        //将传入的数据转为图片信息
        ImageInfoEx imageInfoEx = ImageUtils.GetImageInfoEx(userInfo.getFaceImage());

        //获得人脸信息List
        List<FaceInfo> faceInfosMe = arcfaceWorker
                .detectFace(imageInfoEx);

        //RGB检测
        int judge = arcfaceWorker.detectionRGB(imageInfoEx,faceInfosMe);
        if (judge == -1){
            response.setMsg("未检测出人脸");
            response.setStatusCode(-1);
            return response;
        } else if (judge < 0.5){
            //不是为活体，插入警告信息
            response.setMsg("检测到异常人脸识别行为，警告");
            response.setStatusCode(-1);
            warningDao.AddWarning("检测到异常人脸识别行为，警告");
            return response;
        }

        //获得人脸信息特点
        FaceFeature faceFeatureMe = arcfaceWorker
                .extractFaceFeature(faceInfosMe,imageInfoEx);

        String webImage;


        //将byte[]转为文件
        try {
            String fileName = UUID.randomUUID().toString() +".png";
            String imagePath = path + "/"+ fileName;

            InputStream in = new ByteArrayInputStream(userInfo.getFaceImage());
            BufferedImage bImageFromConvert = ImageIO.read(in);
            ImageIO.write(bImageFromConvert, "png", new File(new String(imagePath.getBytes(StandardCharsets.UTF_8))));
            userInfo.setImage(imagePath);
            webImage = serviceIp+":8080/static/" + fileName;

        } catch (IOException e) {
            response.setMsg("图片转换错误");
            response.setStatusCode(-1);
            return  response;
        }

        User user = new User(userInfo.getName(),faceFeatureMe.getFeatureData());
        user.setImage(webImage);


        //插入到数据库
         judge = userDao.AddUser(user);
        if (judge == 0){
            System.out.println("插入失败");
        }

        userInfo.setFaceImage(user.getFaceImage());
        userInfo.setBase64(null);
        userList.addUser(user.getId(),userInfo);

        response.setStatusCode(0);
        response.setMsg("success");
        return response;
    }


}
