package br.com.smartroll.repository.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private ClassEntity classEntity;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Construtor padrão
    public RollEntity() {}

    // Construtor com parâmetros
    public RollEntity(String longitude, String latitude, String classCode) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.classEntity = new ClassEntity();
        this.classEntity.classCode = classCode;
    }
}
