package com.fuchuang.facerecognition.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserBean {

    private Integer id;

    @JsonProperty("name")
    private String  name;

    @JsonProperty("base64")
    private String base64;

    private byte[] faceImage;

    private  String image;

}
