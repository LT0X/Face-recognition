package com.fuchuang.facerecognition.config;

//import com.arcsoft.face.EngineConfiguration;
//import com.arcsoft.face.FaceEngine;
//import com.arcsoft.face.FunctionConfiguration;
//import com.arcsoft.face.enums.DetectMode;
//import com.arcsoft.face.enums.DetectOrient;
//import com.arcsoft.face.enums.ErrorInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//
//
//@Configuration
//@EnableConfigurationProperties({
//        FunConfigurationProperty.class,
//        EngineConfigurationProperty.class})
//public class ImageArcFaceConfig {
//
//
//    @Autowired
//    private  EngineConfigurationProperty engineConfigurationProperty;
//
//    @Autowired
//    private  FunConfigurationProperty funConfigurationProperty;
//
//    @Bean
//    @Qualifier("ImageArcFace")
//    public FaceEngine faceEngine(){
//        FaceEngine faceEngine = new FaceEngine("D:\\temps\\ArcSoft_ArcFace_Java_Windows_x64_V3.0\\libs\\WIN64");
//        EngineConfiguration engineConfiguration = getFaceEngineConfiguration();
//        int errorCode = faceEngine.init(engineConfiguration);
//        if (errorCode != ErrorInfo.MOK.getValue())
//            throw new RuntimeException("初始化引擎失败");
//        return faceEngine;
//    }
//
//    /**
//     * 初始化引擎配置
//     * @return
//     */
//    private EngineConfiguration getFaceEngineConfiguration() {
//        EngineConfiguration engineConfiguration = new EngineConfiguration();
//        //配置引擎模式
//
//        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
//
//        //配置人脸角度 全角度 ASF_OP_ALL_OUT 不够准确且检测速度慢
//        switch (engineConfigurationProperty.getDetectFaceOrientPriority()){
//            case "ASF_OP_0_ONLY":
//                engineConfiguration
//                        .setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);
//                break;
//            case "ASF_OP_90_ONLY":
//                engineConfiguration
//                        .setDetectFaceOrientPriority(DetectOrient.ASF_OP_90_ONLY);
//                break;
//            case "ASF_OP_270_ONLY":
//                engineConfiguration
//                        .setDetectFaceOrientPriority(DetectOrient.ASF_OP_270_ONLY);
//                break;
//            case "ASF_OP_180_ONLY":
//                engineConfiguration
//                        .setDetectFaceOrientPriority(DetectOrient.ASF_OP_180_ONLY);
//                break;
//            case "ASF_OP_ALL_OUT":
//                engineConfiguration
//                        .setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
//                break;
//            default:
//                engineConfiguration
//                        .setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
//        }
//        //设置识别的最小人脸比 n
//        engineConfiguration
//                .setDetectFaceScaleVal(engineConfigurationProperty.getDetectFaceScale());
//        engineConfiguration
//                .setDetectFaceMaxNum(engineConfigurationProperty.getDetectFaceMaxNum());
//        //功能配置
//        initFuncConfiguration(engineConfiguration);
//        return engineConfiguration;
//    }
//
//    /**
//     * 功能配置
//     * @param engineConfiguration
//     */
//    private void initFuncConfiguration(EngineConfiguration engineConfiguration){
//        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
//        //是否支持年龄检测
//        functionConfiguration.setSupportAge(funConfigurationProperty.isSupportAge());
//        //是否支持3d 检测
//        functionConfiguration
//                .setSupportFace3dAngle(funConfigurationProperty.isSupportFace3dAngle());
//        //是否支持人脸检测
//        functionConfiguration
//                .setSupportFaceDetect(funConfigurationProperty.isSupportFaceDetect());
//        //是否支持人脸识别
//        functionConfiguration
//                .setSupportFaceRecognition(funConfigurationProperty.isSupportFaceRecognition());
//        //是否支持性别检测
//        functionConfiguration
//                .setSupportGender(funConfigurationProperty.isSupportGender());
//        //是否支持活体检测
//        functionConfiguration
//                .setSupportLiveness(funConfigurationProperty.isSupportLiveness());
//        //是否至此IR活体检测
//        functionConfiguration
//                .setSupportIRLiveness(funConfigurationProperty.isSupportIRLiveness());
//        engineConfiguration.setFunctionConfiguration(functionConfiguration);
//    }
//
//}
