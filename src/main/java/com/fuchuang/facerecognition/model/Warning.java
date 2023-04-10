package com.fuchuang.facerecognition.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Time;

@Data
public class Warning {
    private  Integer id;

    @JsonProperty("context")
    private  String context;

    private Time time;

}
