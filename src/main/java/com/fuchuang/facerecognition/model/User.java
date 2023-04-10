package com.fuchuang.facerecognition.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Time;

@Data
public class User {

    private Integer id;

    private String  name;

    private byte[] faceImage;

    private Time createTime;

    private String image;

    public User(String username,byte[] userImage){
        this.faceImage = userImage;
        this.name = username;
    }
    public User(){

    }

}
