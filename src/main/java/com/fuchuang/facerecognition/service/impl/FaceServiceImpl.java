package com.fuchuang.facerecognition.service.impl;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;

import com.arcsoft.face.toolkit.ImageInfoEx;
import com.fuchuang.facerecognition.Init.UserList;
import com.fuchuang.facerecognition.dao.UserDao;
import com.fuchuang.facerecognition.model.UserFace;
import com.fuchuang.facerecognition.service.FaceService;
import com.fuchuang.facerecognition.utils.ArcfaceWorker;
import com.fuchuang.facerecognition.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FaceServiceImpl implements FaceService {

    @Autowired
    private ArcfaceWorker arcfaceWorker;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserList userList;


    @Override
    public float faceSimilar(byte[] bytes1, byte[] bytes2) {

        //将传入的数据转为图片信息
        ImageInfoEx imageInfoEx = ImageUtils.GetImageInfoEx(bytes1);
        ImageInfoEx imageInfoEx2 = ImageUtils.GetImageInfoEx(bytes2);


        //获得人脸信息List
        List<FaceInfo> faceInfosMe = arcfaceWorker
                .detectFace(imageInfoEx);
        List<FaceInfo> faceInfosMe2 = arcfaceWorker
                .detectFace(imageInfoEx2);
        if (faceInfosMe.size() == 0 || faceInfosMe2.size() == 0){
            return  -1;
        }

        //获得人脸信息特点
        FaceFeature faceFeatureMe = arcfaceWorker
                .extractFaceFeature(faceInfosMe,imageInfoEx);
        FaceFeature faceFeatureMe2 = arcfaceWorker
                .extractFaceFeature(faceInfosMe2,imageInfoEx2);

        //比对相似度
        FaceSimilar faceSimilar = arcfaceWorker.
                compareFaceFeature(faceFeatureMe.getFeatureData(), faceFeatureMe2.getFeatureData());
        return  faceSimilar.getScore();

    }

    @Override
    public List<UserFace> getUserFace() {


        //开始查询用户全部人脸
        List<UserFace> userFaces = userDao.GetUserFace();

        return userFaces;
    }

    @Override
    public Boolean deleteUserFace(Integer id) {

        String path = userDao.GetUserImage(id);

        String fileName = path.substring(path.lastIndexOf('/')+1,path.length());

        int x = userDao.DeleteUserFace(id);

        userList.getMap().remove(id);

        deleteFile(fileName);
        if (x < 0){
            return  false;
        }
        return  true;
    }

    public static boolean deleteFile(String fileName) {
        fileName = "/project/projectImage/faceimage/" + fileName;
        File file = new File(fileName);
        // 如果文件路径只有单个文件
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println(fileName + "不存在！");
            return false;
        }
    }



}
