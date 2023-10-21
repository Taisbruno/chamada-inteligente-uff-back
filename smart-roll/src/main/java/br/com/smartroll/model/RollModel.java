package br.com.smartroll.model;

import br.com.smartroll.repository.entity.RollEntity;

public class RollModel {
    public String id;
    public String longitude;
    public String latitude;
    public String class_code;
    public String createdAt;
    public String finishedAt;

    public RollModel(String longitude, String latitude, String class_code){
        this.longitude = longitude;
        this.latitude = latitude;
        this.class_code = class_code;
    }

    public RollModel(RollEntity rollEntity){
        this.longitude = rollEntity.longitude;
        this.latitude = rollEntity.latitude;
        this.class_code = rollEntity.classEntity.classCode;
        this.createdAt = String.valueOf(rollEntity.createdAt);
        this.finishedAt = String.valueOf(rollEntity.finishedAt);
    }
}
