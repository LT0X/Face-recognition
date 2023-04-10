package com.fuchuang.facerecognition.service;

import com.arcsoft.face.toolkit.ImageInfoEx;
import com.fuchuang.facerecognition.model.Response;
import com.fuchuang.facerecognition.model.User;
import com.fuchuang.facerecognition.model.UserBean;

public interface UserLoginService {

    Response login(byte[] image);

    Response register(UserBean userInfo);


}
