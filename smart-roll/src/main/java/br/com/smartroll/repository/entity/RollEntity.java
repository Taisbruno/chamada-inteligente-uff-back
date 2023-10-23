package br.com.smartroll.repository.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa a entidade "Roll" no banco de dados.
 */
@Entity
@Table(name = "roll")
@EntityListeners(AuditingEntityListener.class)
public class RollEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "longitude", nullable = false)
    public String longitude;
    @Column(name = "latitude", nullable = false)
    public String latitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_code", nullable = false)
    public ClassEntity classEntity;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;
    @Column(name = "finished_at")
    public LocalDateTime finishedAt;
    @OneToMany(mappedBy = "roll", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PresenceEntity> presences;

    /**
     * Construtor padrão de RollEntity.
     */
    public RollEntity() {}

    /**
     * Construtor parametrizado de RollEntity.
     *
     * @param longitude Longitude onde a chamada foi realizada.
     * @param latitude Latitude onde a chamada foi realizada.
     * @param classCode Código da turma para a qual a chamada é direcionada.
     */
    public RollEntity(String longitude, String latitude, String classCode) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.classEntity = new ClassEntity();
        this.classEntity.classCode = classCode;
    }
}
