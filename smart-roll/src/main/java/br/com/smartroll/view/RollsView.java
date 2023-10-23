package br.com.smartroll.view;

import br.com.smartroll.model.RollModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RollsView {

    @SerializedName("rolls")
    private List<RollView> rolls = new ArrayList<>();

    public RollsView(List<RollModel> rollsModel) {
        for(RollModel rollModel : rollsModel){
            RollView rollView = new RollView(rollModel);
            rolls.add(rollView);
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
