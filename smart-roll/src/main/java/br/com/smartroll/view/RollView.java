package br.com.smartroll.view;

import br.com.smartroll.model.RollModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class RollView {
    @SerializedName("id")
    public String id;
    @SerializedName("longitude")
    public String longitude;
    @SerializedName("latitude")
    public String latitude;
    @SerializedName("classCode")
    public String classCode;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("finishedAt")
    public String finishedAt;
    @SerializedName("isOpen")
    public boolean isOpen;
    @SerializedName("scheduleCloseTime")
    public String scheduledCloseTime;

    public RollView(RollModel rollModel) {
        this.id = rollModel.id;
        this.longitude = rollModel.longitude;
        this.latitude = rollModel.latitude;
        this.classCode = rollModel.class_code;
        this.createdAt = rollModel.createdAt;
        this.finishedAt = rollModel.finishedAt;
        this.isOpen = rollModel.isOpen;
        this.scheduledCloseTime = rollModel.scheduledCloseTime;
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
