package com.fuchuang.facerecognition.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response {

    private  int statusCode;

    private String msg;

}
