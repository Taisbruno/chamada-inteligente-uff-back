package br.com.smartroll.view;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import br.com.smartroll.repository.entity.UserEntity;

public class UserView {
    @SerializedName("registration")
    public String registration;

    @SerializedName("name")
    public String name;

    @SerializedName("cpf")
    public String cpf;

    @SerializedName("email")
    public String email;

    @SerializedName("type")
    public String type;

    public UserView(UserEntity userEntity) {
        this.registration = userEntity.registration;
        this.name = userEntity.name;
        this.cpf = userEntity.cpf;
        this.email = userEntity.email;
        this.type = userEntity.type;
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
