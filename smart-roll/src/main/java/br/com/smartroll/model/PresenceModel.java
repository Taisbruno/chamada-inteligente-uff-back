package br.com.smartroll.model;

import java.time.LocalDateTime;

public class PresenceModel {

    public String id;
    public String studentRegistration;
    public String rollId;
    public String medicalCertificate;
    public String message;
    public boolean isPresent;
    public String timePresent;

    public PresenceModel(String studentRegistration, String rollId, String medicalCertificate, String message){
        this.studentRegistration = studentRegistration;
        this.rollId = rollId;
        this.medicalCertificate = medicalCertificate;
        this.message = message;
        this.isPresent = true;
        this.timePresent = LocalDateTime.now().toString();
    }
}
