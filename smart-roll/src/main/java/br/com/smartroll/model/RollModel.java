package br.com.smartroll.model;

import br.com.smartroll.repository.entity.RollEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RollModel {
    public String id;
    public String longitude;
    public String latitude;
    public String class_code;
    public String createdAt;
    public String finishedAt;
    public double presencePercentage;
    public String presenceTimeAvarage;
    public boolean isOpen;
    public List<PresenceModel> presences;
    public String scheduledCloseTime;

    public RollModel(String longitude, String latitude, String class_code){
        this.longitude = longitude;
        this.latitude = latitude;
        this.class_code = class_code;
    }

    public RollModel(RollEntity rollEntity){
        this.id = String.valueOf(rollEntity.id);
        this.longitude = rollEntity.longitude;
        this.latitude = rollEntity.latitude;
        this.createdAt = String.valueOf(rollEntity.createdAt);
        this.finishedAt = String.valueOf(rollEntity.finishedAt);
        this.presences = new ArrayList<>();
        this.scheduledCloseTime = String.valueOf(rollEntity.scheduledCloseTime);
    }
}
