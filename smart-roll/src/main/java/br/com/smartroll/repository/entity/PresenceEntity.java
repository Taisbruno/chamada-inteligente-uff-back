package br.com.smartroll.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "presence")
public class PresenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "student_registration", nullable = false)
    public String studentRegistration;
    @Column(name = "id_call", nullable = false)
    public String idCall;
    @Column(name = "certificate", nullable = false)
    public String medicalCertificate;
    @Column(name = "message", nullable = false)
    public String message;
    @Column(name = "is_present", nullable = false)
    public boolean isPresent;
    @Column(name = "time_present", nullable = false)
    public String timePresent;

    public PresenceEntity() {}

    public PresenceEntity(String studentRegistration, String idCall, String medicalCertificate,
                          String message, boolean isPresent, String timePresent) {
        this.studentRegistration = studentRegistration;
        this.idCall = idCall;
        this.medicalCertificate = medicalCertificate;
        this.message = message;
        this.isPresent = isPresent;
        this.timePresent = timePresent;
    }
}
