package br.com.smartroll.view;

import br.com.smartroll.model.ScheduleModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SchedulesView {

    @SerializedName("schedules")
    public List<ScheduleView> schedules = new ArrayList<>();

    public SchedulesView(List<ScheduleModel> schedulesModel) {
        for(ScheduleModel scheduleModel : schedulesModel){
            ScheduleView scheduleView = new ScheduleView(scheduleModel);
            schedules.add(scheduleView);
        }
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
