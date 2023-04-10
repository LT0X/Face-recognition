package com.fuchuang.facerecognition.contrroller;


import com.arcsoft.face.toolkit.ImageInfoEx;
import com.fuchuang.facerecognition.model.Response;
import com.fuchuang.facerecognition.model.User;
import com.fuchuang.facerecognition.model.UserBean;
import com.fuchuang.facerecognition.service.UserLoginService;
import com.fuchuang.facerecognition.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private  ImageUtils imageUtils;

    @PostMapping("/face/user/login")
    public Response UserLogin(@RequestBody UserBean userInfo)  {

        //将base64转为byte[]数组
        byte[] bytes = imageUtils.base64TransformImg(userInfo.getBase64());

        //进行人脸登录验证
        Response res = userLoginService.login(bytes);
        return res;

    }

    @PostMapping("face/user/register")
    public Response UserRegister(@RequestBody UserBean userInfo) {

        byte[] bytes = imageUtils.base64TransformImg(userInfo.getBase64());
        userInfo.setFaceImage(bytes);
        Response res = userLoginService.register(userInfo);
        return  res;
    }



//    @PostMapping("face/user/register")
//    public Response UserRegister(@RequestParam String name,
//                                 @RequestParam MultipartFile image) throws IOException {
//
//        //进行人脸注册
//        Response res = userLoginService.register(name, image.getBytes());
//        return  res;
//    }


}
