package br.com.smartroll.repository.entity;

import br.com.smartroll.model.PresenceModel;

import javax.persistence.*;

/**
 * Representa a entidade "Presence" no banco de dados.
 */
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
    @Column(name = "certificate")
    public String medicalCertificate;
    @Column(name = "message", nullable = false)
    public String message;
    @Column(name = "is_present", nullable = false)
    public boolean isPresent;
    @Column(name = "entry_time", nullable = false)
    public String entryTime;
    @Column(name = "exit_time")
    public String exitTime;

    public PresenceEntity() {}

    public PresenceEntity(String studentRegistration, String idCall, String medicalCertificate,
                          String message, boolean isPresent, String entryTime) {
        this.studentRegistration = studentRegistration;
        this.roll = new RollEntity();
        this.roll.id = Long.valueOf(idCall);
        this.medicalCertificate = medicalCertificate;
        this.message = message;
        this.isPresent = isPresent;
        this.entryTime = entryTime;
    }

    /**
     * Construtor que transforma um modelo de presença em uma entidade de presença.
     *
     * @param presenceModel Modelo de presença a ser transformado.
     */
    public PresenceEntity(PresenceModel presenceModel) {
        this.studentRegistration = presenceModel.studentRegistration;
        this.roll = new RollEntity();
        this.roll.id = Long.valueOf(presenceModel.rollId);
        this.medicalCertificate = presenceModel.medicalCertificate;
        this.message = presenceModel.message;
        this.isPresent = presenceModel.isPresent;
        this.entryTime = presenceModel.entryTime;
    }
}
