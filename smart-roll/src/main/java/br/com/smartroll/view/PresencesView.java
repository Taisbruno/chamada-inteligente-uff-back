package br.com.smartroll.view;

import br.com.smartroll.model.PresenceModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PresencesView {

    @SerializedName("presences")
    public List<PresenceView> presences = new ArrayList<>();

    public PresencesView(List<PresenceModel> presencesModel) {
        for(PresenceModel presenceModel : presencesModel){
            PresenceView presenceView = new PresenceView(presenceModel);
            this.presences.add(presenceView);
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
