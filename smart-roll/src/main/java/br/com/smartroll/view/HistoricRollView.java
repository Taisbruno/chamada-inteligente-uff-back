package br.com.smartroll.view;

import br.com.smartroll.model.PresenceModel;
import br.com.smartroll.model.RollModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HistoricRollView {

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
    @SerializedName("studentsPresent")
    public int studentsPresent;
    @SerializedName("presences")
    public List<PresenceView> presences = new ArrayList<>();

    public HistoricRollView(RollModel rollModel) {
        this.id = rollModel.id;
        this.longitude = rollModel.longitude;
        this.latitude = rollModel.latitude;
        this.classCode = rollModel.class_code;
        this.createdAt = rollModel.createdAt;
        this.finishedAt = rollModel.finishedAt;
        for(PresenceModel presenceModel : rollModel.presences){
            PresenceView presenceView = new PresenceView(presenceModel);
            this.presences.add(presenceView);
        }
        this.studentsPresent = this.presences.size();
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
