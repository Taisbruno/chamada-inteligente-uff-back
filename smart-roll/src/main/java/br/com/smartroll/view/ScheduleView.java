package br.com.smartroll.view;

import br.com.smartroll.model.ScheduleModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ScheduleView {

    @SerializedName("id")
    public String id;
    @SerializedName("classCode")
    public String classCode;
    @SerializedName("dayOfWeek")
    public int dayOfWeek;
    @SerializedName("startTime")
    public String startTime;
    @SerializedName("endTime")
    public String endTime;
    @SerializedName("longitude")
    public String longitude;
    @SerializedName("latitude")
    public String latitude;

    public ScheduleView(ScheduleModel scheduleModel) {
        this.id = scheduleModel.id;
        this.classCode = scheduleModel.classCode;
        this.dayOfWeek = scheduleModel.dayOfWeek;
        this.startTime = scheduleModel.startTime;
        this.endTime = scheduleModel.endTime;
        this.longitude = scheduleModel.longitude;
        this.latitude = scheduleModel.latitude;
    }

    /**
     * Retorna a view em formato de Json.
     * @return Uma String com formatação de Json da view.
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
