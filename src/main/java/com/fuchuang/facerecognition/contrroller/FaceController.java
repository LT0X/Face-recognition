package com.fuchuang.facerecognition.contrroller;

import com.fuchuang.facerecognition.model.UserFace;
import com.fuchuang.facerecognition.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FaceController {

    @Autowired
    FaceService faceService;


    @PostMapping("/face/similar")
    public Map<String,String> faceSimilar(@RequestParam(required = false) MultipartFile picture1,
                                          @RequestParam(required = false) MultipartFile picture2){

        Map<String,String> map = new HashMap<String,String>();
        map.put("statusCode","0");
        map.put("msg","success");
        map.put("similar","-1");
        byte[] bytes1,bytes2;

        //解析参数
        try {
             bytes1= picture1.getBytes();
             bytes2= picture2.getBytes();
        } catch (Exception e) {
            map.put("statusCode","-1");
            map.put("msg","文件转换错误或无文件上传");
            e.printStackTrace();
            return  map;
        }
        //进行业务逻辑处理
        float res = faceService.faceSimilar(bytes1,bytes2);
        if (res  == -1){
            map.put("msg","图片未检测出人脸");
            return map;
        }
        map.put("similar",Float.toString(res));
        return map;
    }

    @GetMapping("/face/userface")
    public List<UserFace> getUserFace(){

        //开始查询
        List<UserFace> userFace = faceService.getUserFace();

        return  userFace;
    }

    @DeleteMapping("/face/deleteface")
    public  Map<String,String> deleteUser(@RequestParam Integer id){

        Map<String,String> map = new HashMap<String,String>();
        map.put("statusCode","0");
        map.put("msg","success");

        //开始删除用户人脸信息
        Boolean judge = faceService.deleteUserFace(id);

        if (judge == false){
            map.put("statusCode","0");
            map.put("msg","删除失败");
            return  map;
        }
        return map;
    }


     
}
