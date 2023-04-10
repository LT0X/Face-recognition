package com.fuchuang.facerecognition;



import com.fuchuang.facerecognition.utils.ArcfaceWorker;
import com.fuchuang.facerecognition.utils.ImageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.arcsoft.face.*;
import com.arcsoft.face.enums.*;
import com.arcsoft.face.toolkit.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.arcsoft.face.toolkit.ImageInfoEx;
import org.springframework.boot.web.servlet.ServletComponentScan;

import static com.arcsoft.face.toolkit.ImageFactory.getGrayData;
import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;


@ServletComponentScan(basePackages = "com.fuchuang")
@SpringBootTest(classes = {FaceRecognitionApplication.class})
class FaceRecognitionApplicationTests {


    @Autowired
    private ArcfaceWorker arcfaceWorker;

   

    @Test
    public void testDetectFacesDemo01() {
       
        FaceInfo faceInfoA = getFaceInfo(new File("C:\\Pictures\\CameraRoll\\Me3.jpg"));
        int A = faceInfoA.getOrient();

        FaceInfo faceInfoB = getFaceInfo(new File("C:\\Pictures\\CameraRoll\\Me4.jpg"));
        int B = faceInfoB.getOrient();

        FaceInfo faceInfoC = getFaceInfo(new File("C:\\Pictures\\CameraRoll\\Me5.jpg"));
        int C = faceInfoC.getOrient();
        System.out.println("正脸："+ A);
        System.out.println("左脸："+ B);
        System.out.println("右脸："+ C);

    }

    public FaceInfo getFaceInfo(File file){
       ImageUtils.ImageInfoMeta imageInfoMeta =ImageUtils.packImageInfoEx(file);
        List<FaceInfo> faceInfos = arcfaceWorker
                .detectFace(imageInfoMeta.getImageInfoEx());
        return faceInfos.get(0);
    }

    @Test
    public void testextractFaceFeature() {
        File file = new File("C:\\Users\\14530\\Pictures\\Camera Roll\\idCard02.jpg");
       ImageUtils.ImageInfoMeta imageInfoMeta =ImageUtils.packImageInfoEx(file);
        List<FaceInfo> faceInfos = arcfaceWorker
                .detectFace(imageInfoMeta.getImageInfoEx());
        FaceFeature faceFeature = arcfaceWorker
                .extractFaceFeature(faceInfos, imageInfoMeta.getImageInfoEx());
        System.out.println(faceFeature);
    }

    @Test
    public void testCompareFaceFeature () {
        //提取身份证的人脸特征
        File fileCard = new File("C:\\Pictures\\Camera Roll\\idCard03.jpg");
       ImageUtils.ImageInfoMeta imageInfoMetaCard =ImageUtils
                .packImageInfoEx(fileCard);
        List<FaceInfo> faceInfosCard = arcfaceWorker
                .detectFace(imageInfoMetaCard.getImageInfoEx());
        FaceFeature faceFeatureCard = arcfaceWorker
                .extractFaceFeature(faceInfosCard, imageInfoMetaCard.getImageInfoEx());

        //提取目标的人脸特征
        File fileMe = new File("C:\\Camera Roll\\me1.jpg");
       ImageUtils.ImageInfoMeta imageInfoMetaMe = ImageUtils
                .packImageInfoEx(fileMe);
        List<FaceInfo> faceInfosMe = arcfaceWorker
                .detectFace(imageInfoMetaMe.getImageInfoEx());
        FaceFeature faceFeatureMe = arcfaceWorker
                .extractFaceFeature(faceInfosMe, imageInfoMetaMe.getImageInfoEx());

        FaceSimilar faceSimilar = arcfaceWorker
                .compareFaceFeature(faceFeatureCard, faceFeatureMe, CompareModel.ID_PHOTO);
        System.out.println(faceSimilar.getScore());
    }


    @Test
    void contextLoads() {
        //从官网获取
        String appId = "9zoxQ4EBCw3KgzkH9vK3VFZjtFUvPV4stNmrEMLwF2Ym";
        String sdkKey = "9VS1cjhCCx9usn8kZ165cvTtyNrPjku1e5DdAGhPqrnN";


        FaceEngine faceEngine = new FaceEngine("D:\\temps\\ArcSoft_ArcFace_Java_Windows_x64_V3.0\\libs\\WIN64");
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }


        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);


        //初始化引擎
         errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }


        //人脸检测
        ImageInfo imageInfo = getRGBData(new File("D:\\projectImg\\test1.jpg"));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        System.out.println(faceInfoList);

        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

        //人脸检测2
        ImageInfo imageInfo2 = getRGBData(new File("D:\\projectImg\\test2.jpg"));
        List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2);
        System.out.println(faceInfoList2);

        //特征提取2
        FaceFeature faceFeature2 = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2.get(0), faceFeature2);
        System.out.println("特征值大小：" + faceFeature2.getFeatureData().length);

        //特征比对
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
        FaceSimilar faceSimilar = new FaceSimilar();

        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

        System.out.println("相似度：" + faceSimilar.getScore());

        //设置活体测试
        errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
        //人脸属性检测
        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportFace3dAngle(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);


        //性别检测
        List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
        errorCode = faceEngine.getGender(genderInfoList);
        System.out.println("性别：" + genderInfoList.get(0).getGender());

        //年龄检测
        List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
        errorCode = faceEngine.getAge(ageInfoList);
        System.out.println("年龄：" + ageInfoList.get(0).getAge());

        //3D信息检测
        List<Face3DAngle> face3DAngleList = new ArrayList<Face3DAngle>();
        errorCode = faceEngine.getFace3DAngle(face3DAngleList);
        System.out.println("3D角度：" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());

        //活体检测
        List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
        errorCode = faceEngine.getLiveness(livenessInfoList);
        System.out.println("活体：" + livenessInfoList.get(0).getLiveness());


        //IR属性处理
        ImageInfo  imageInfoGray = getGrayData(new File("D:\\projectImg\\IR2.jpg"));
        List<FaceInfo> faceInfoListGray = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray);

        FunctionConfiguration configuration2 = new FunctionConfiguration();
        configuration2.setSupportIRLiveness(true);
        errorCode = faceEngine.processIr(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray, configuration2);
        //IR活体检测

        List<IrLivenessInfo> irLivenessInfo = new ArrayList<>();
        errorCode = faceEngine.getLivenessIr(irLivenessInfo);
        System.out.println("IR活体：" + irLivenessInfo.get(0).getLiveness());

        ImageInfoEx imageInfoEx = new ImageInfoEx();
        imageInfoEx.setHeight(imageInfo.getHeight());
        imageInfoEx.setWidth(imageInfo.getWidth());
        imageInfoEx.setImageFormat(imageInfo.getImageFormat());
        imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
        imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
        List<FaceInfo> faceInfoList1 = new ArrayList<>();
        errorCode = faceEngine.detectFaces(imageInfoEx, DetectModel.ASF_DETECT_MODEL_RGB, faceInfoList1);

        FunctionConfiguration fun = new FunctionConfiguration();
        fun.setSupportAge(true);
        errorCode = faceEngine.process(imageInfoEx, faceInfoList1, functionConfiguration);
        List<AgeInfo> ageInfoList1 = new ArrayList<>();
        int age = faceEngine.getAge(ageInfoList1);
        System.out.println("年龄：" + ageInfoList1.get(0).getAge());

        FaceFeature feature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfoEx, faceInfoList1.get(0), feature);


        //引擎卸载
        errorCode = faceEngine.unInit();
    }
}




