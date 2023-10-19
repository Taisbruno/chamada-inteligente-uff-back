package br.com.smartroll.model;

public class RollModel {
    public String id;
    public String longitude;
    public String latitude;
    public String class_code;
    public String createdAt;

    public RollModel(String longitude, String latitude, String class_code){
        this.longitude = longitude;
        this.latitude = latitude;
        this.class_code = class_code;
    }

    public RollModel(String id, String longitude, String latitude, String class_code, String createdAt){
        this.longitude = longitude;
        this.latitude = latitude;
        this.class_code = class_code;
        this.createdAt = createdAt;
    }
}
