package br.com.smartroll.model;

import br.com.smartroll.repository.entity.PresenceEntity;

import java.time.Duration;
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
    public String entryTime;
    public String exitTime;
    public String timePresent;
    public double frequency;
    public boolean failed;

    public PresenceModel(String studentRegistration, String rollId, String message){
        this.studentRegistration = studentRegistration;
        this.rollId = rollId;
        this.message = message;
        this.isPresent = true;
        this.entryTime = LocalDateTime.now().toString();
    }

    public PresenceModel(String studentRegistration, String rollId, String medicalCertificate, String message){
        this.studentRegistration = studentRegistration;
        this.rollId = rollId;
        this.medicalCertificate = medicalCertificate;
        this.message = message;
        this.isPresent = false;
        this.entryTime = LocalDateTime.now().toString();
    }

    public PresenceModel(long id, String studentRegistration, long rollId, String medicalCertificate, String message, boolean isPresent, String entryTime, String exitTime){
        this.id = String.valueOf(id);
        this.studentRegistration = studentRegistration;
        this.rollId = String.valueOf(rollId);
        this.medicalCertificate = medicalCertificate;
        this.message = message;
        this.isPresent = isPresent;
        this.entryTime = String.valueOf(entryTime);
        this.exitTime = String.valueOf(exitTime);
    }

    /**
     * Construtor alternativo para geração de histórico de chamadas ocorridas.
     * @param presenceEntity entidade presença.
     */
    public PresenceModel(PresenceEntity presenceEntity){
        this.id = String.valueOf(presenceEntity.id);
        this.studentRegistration = presenceEntity.studentRegistration;
        this.rollId = String.valueOf(presenceEntity.roll.id);
        this.medicalCertificate = presenceEntity.medicalCertificate;
        this.message = presenceEntity.message;
        this.isPresent = presenceEntity.isPresent;
        this.entryTime = String.valueOf(presenceEntity.entryTime);
        this.exitTime = String.valueOf(presenceEntity.exitTime);
        // Calculate the duration between entry and exit time
        LocalDateTime entry = LocalDateTime.parse(presenceEntity.entryTime);
        LocalDateTime exit = LocalDateTime.parse(presenceEntity.exitTime);
        Duration duration = Duration.between(entry, exit);
        // Convert the duration to a formatted string (e.g., "02:15:30" for 2 hours, 15 minutes, and 30 seconds)
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        this.timePresent = String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
