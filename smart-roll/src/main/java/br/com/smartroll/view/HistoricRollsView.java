package br.com.smartroll.view;

import br.com.smartroll.model.RollModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HistoricRollsView {

    @SerializedName("rolls")
    private List<HistoricRollView> rolls = new ArrayList<>();

    public HistoricRollsView(List<RollModel> rollsModel) {
        for(RollModel rollModel : rollsModel){
            HistoricRollView rollView = new HistoricRollView(rollModel);
            this.rolls.add(rollView);
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
