package br.com.smartroll.view;

import br.com.smartroll.repository.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class StopWatchView {
    @SerializedName("hours")
    public String hours;

    @SerializedName("minutes")
    public String minutes;

    @SerializedName("seconds")
    public String seconds;

    public StopWatchView(String hours, String minutes, String seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
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
