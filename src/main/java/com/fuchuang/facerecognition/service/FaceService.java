package com.fuchuang.facerecognition.service;

import com.fuchuang.facerecognition.model.UserFace;

import java.util.List;

public interface FaceService {

    float faceSimilar(byte[] bytes1,byte[] bytes2);

    List<UserFace> getUserFace();

    Boolean deleteUserFace(Integer id);

}
