package br.com.smartroll.repository.entity;

import br.com.smartroll.model.PresenceModel;

import javax.persistence.*;

@Entity
@Table(name = "presence")
public class PresenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "student_registration", nullable = false)
    public String studentRegistration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roll_id", nullable = false)
    public RollEntity roll;
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
        this.roll = new RollEntity();
        this.roll.id = Long.valueOf(idCall);
        this.medicalCertificate = medicalCertificate;
        this.message = message;
        this.isPresent = isPresent;
        this.timePresent = timePresent;
    }

    public PresenceEntity(PresenceModel presenceModel) {
        this.studentRegistration = presenceModel.studentRegistration;
        this.roll = new RollEntity();
        this.roll.id = Long.valueOf(presenceModel.rollId);
        this.medicalCertificate = presenceModel.medicalCertificate;
        this.message = presenceModel.message;
        this.isPresent = presenceModel.isPresent;
        this.timePresent = presenceModel.timePresent;
    }
}
