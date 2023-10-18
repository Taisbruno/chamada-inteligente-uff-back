package br.com.smartroll.repository.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "class_schedule")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "class_code", nullable = false)
    public ClassEntity classEntity;

    @Column(name = "day_of_week", nullable = false)
    public int dayOfWeek;

    @Column(name = "start_time", nullable = false)
    public Time startTime;

    @Column(name = "end_time", nullable = false)
    public Time endTime;

    public ScheduleEntity() {
        // Construtor padrão
    }

    public ScheduleEntity(ClassEntity classEntity, int dayOfWeek, Time startTime, Time endTime) {
        this.classEntity = classEntity;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

