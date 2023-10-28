package br.com.smartroll.model;

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
}
