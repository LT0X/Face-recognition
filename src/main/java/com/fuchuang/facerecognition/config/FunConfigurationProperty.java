package com.fuchuang.facerecognition.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


@Data
@ConfigurationProperties(prefix = "arcface.function-configuration")
public class FunConfigurationProperty {
    private boolean supportFace3dAngle;
    private boolean supportFaceDetect;
    private boolean supportFaceRecognition;
    private boolean supportGender;
    private boolean supportAge;
    private boolean supportLiveness;
    private boolean supportIRLiveness;
}
