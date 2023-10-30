package br.com.smartroll.model;

import br.com.smartroll.repository.entity.ScheduleEntity;

import java.sql.Time;

/**
 * dayOfWeek
 * 0 ou 7 - Domingo
 * 1 - Segunda-feira
 * 2 - Terça-feira
 * 3 - Quarta-feira
 * 4 - Quinta-feira
 * 5 - Sexta-feira
 * 6 - Sábado
 */
public class ScheduleModel {

    public String id;

    public String classCode;

    public int dayOfWeek;

    public String startTime;

    public String endTime;

    public String longitude;

    public String latitude;

    public ScheduleModel(String classCode, int dayOfWeek, String startTime, String endTime, String longitude, String latitude) {
        this.classCode = classCode;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public ScheduleModel(ScheduleEntity scheduleEntity) {
        this.classCode = scheduleEntity.classEntity.classCode;
        this.dayOfWeek = scheduleEntity.dayOfWeek;
        this.startTime = String.valueOf(scheduleEntity.startTime);
        this.endTime = String.valueOf(scheduleEntity.endTime);
        this.longitude = scheduleEntity.longitude;
        this.latitude = scheduleEntity.latitude;
    }
}
