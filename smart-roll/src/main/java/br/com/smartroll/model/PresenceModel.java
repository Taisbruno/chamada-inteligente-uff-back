package br.com.smartroll.model;

import br.com.smartroll.repository.entity.PresenceEntity;

import java.time.LocalDateTime;

public class PresenceModel {

    public String id;
    public String studentRegistration;
    public String name;
    public String rollId;
    public String classCode;
    public String medicalCertificate;
    public String message;
    public boolean isPresent;
    public String timePresent;
    public double frequency;
    public boolean failed;

    public PresenceModel(String studentRegistration, String rollId, String medicalCertificate, String message){
        this.studentRegistration = studentRegistration;
        this.rollId = rollId;
        this.medicalCertificate = medicalCertificate;
        this.message = message;
        this.isPresent = true;
        this.timePresent = LocalDateTime.now().toString();
    }

    public PresenceModel(PresenceEntity presenceEntity){
        this.studentRegistration = presenceEntity.studentRegistration;
        this.rollId = String.valueOf(presenceEntity.roll.id);
        this.medicalCertificate = presenceEntity.medicalCertificate;
        this.message = presenceEntity.message;
        this.isPresent = presenceEntity.isPresent;
        this.timePresent = String.valueOf(presenceEntity.timePresent);
    }

}
