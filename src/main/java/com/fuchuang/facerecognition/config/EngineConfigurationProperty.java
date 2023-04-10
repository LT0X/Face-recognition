package com.fuchuang.facerecognition.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "arcface.engine-configuration")

public class EngineConfigurationProperty {
    private String detectMode;
    private String detectFaceOrientPriority;
    private Integer detectFaceScale;
    private Integer detectFaceMaxNum;
}
