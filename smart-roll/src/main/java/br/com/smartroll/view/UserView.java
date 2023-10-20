package br.com.smartroll.view;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import br.com.smartroll.repository.entity.UserEntity;

/**
 * Representa uma visualização simplificada de um usuário.
 * Esta classe é utilizada principalmente para serialização e apresentação dos dados de um usuário.
 */
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

    /**
     * Construtor de UserView baseado em uma entidade de usuário.
     * Inicializa a visualização do usuário usando os dados da entidade fornecida.
     *
     * @param userEntity A entidade do usuário que será transformada em uma visualização de usuário.
     */
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
