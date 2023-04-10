package com.fuchuang.facerecognition.utils;


import com.arcsoft.face.*;
import com.arcsoft.face.enums.CompareModel;
import com.arcsoft.face.enums.DetectModel;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfoEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArcfaceWorker {


    @Autowired
    @Qualifier("VideoArcFace")
    private  FaceEngine videoEngine;

    @Autowired
    @Qualifier("ImageArcFace")
    private  FaceEngine faceEngine;

    /**
     * 特征提取
     * @param faceInfoList
     * @param imageInfoEx
     * @return
     */
    public FaceFeature extractFaceFeature(
            List<FaceInfo> faceInfoList, ImageInfoEx imageInfoEx) {

        if (faceInfoList == null || imageInfoEx == null)
            return null;
        FaceFeature faceFeature = new FaceFeature();
        int i = faceEngine
                .extractFaceFeature(imageInfoEx, faceInfoList.get(0), faceFeature);
        checkEngineResult(i, ErrorInfo.MOK.getValue(), "人脸特征提取失败");
        return faceFeature;
    }

    //比对人脸特征，获取特征值
    public FaceSimilar compareFaceFeature(byte[] target,byte[] source){
        //特征1
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(target);
        //特征2
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(source);
        FaceSimilar faceSimilar = new FaceSimilar();
        int i = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
        return  faceSimilar;

    }

    //RGB检测
    public int detectionRGB(ImageInfoEx imageInfoEx,List<FaceInfo> list){
        //初始化配置
        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportFace3dAngle(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        //加载配置人脸信息
        int errorCode = faceEngine.process(imageInfoEx, list, configuration);
        List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
        //返回人脸识别信息
        errorCode = faceEngine.getLiveness(livenessInfoList);
        if (livenessInfoList.size() == 0){
            return  -1;
        }
        return livenessInfoList.get(0).getLiveness();
    }

    //Ir属性检测
    public int detectionIR(ImageInfoEx imageInfoEx,List<FaceInfo> list){

        //初始化配置
        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportFace3dAngle(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        //加载配置人脸信息
        int errorCode = faceEngine.processIr(imageInfoEx, list, configuration);
        List<IrLivenessInfo> irLivenessInfo = new ArrayList<>();
        errorCode = faceEngine.getLivenessIr(irLivenessInfo);
        System.out.println("IR活体：" + irLivenessInfo.get(0).getLiveness());
        if (irLivenessInfo.size() == 0){
            return  -1;
        }
        return irLivenessInfo.get(0).getLiveness();

    }




    public FaceSimilar compareFaceFeature(
            FaceFeature target, FaceFeature source, CompareModel compareModel) {
        FaceSimilar faceSimilar = new FaceSimilar();
        int i = faceEngine
                .compareFaceFeature(target, source, compareModel, faceSimilar);
        checkEngineResult(i, ErrorInfo.MOK.getValue(), "人脸特征对比失败");
        return faceSimilar;
    }


    /**
     * 人脸检测
     * @return
     */
    public List<FaceInfo> detectFace(ImageInfoEx imageInfoEx) {
        if (imageInfoEx == null)
            return null;
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        int i = faceEngine
                .detectFaces(imageInfoEx, DetectModel.ASF_DETECT_MODEL_RGB, faceInfoList);
        checkEngineResult(i, ErrorInfo.MOK.getValue(), "人脸检测失败");
        return faceInfoList;
    }


    /**
     * 错误检测
     * @param errorCode
     * @param sourceCode
     * @param errMsg
     */
    private void checkEngineResult(int errorCode, int sourceCode, String errMsg) {
        if (errorCode != sourceCode)
            throw new RuntimeException(errMsg);
    }



}
