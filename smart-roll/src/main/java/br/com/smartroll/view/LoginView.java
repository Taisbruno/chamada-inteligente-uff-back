package br.com.smartroll.view;

import br.com.smartroll.repository.entity.UserEntity;
import com.google.gson.Gson;

public class LoginView {

    public String cpf;

    public String password;

    public LoginView(String cpf, String password) {
        this.cpf = cpf;
        this.password = password;
    }

    public LoginView(UserEntity user) {
        this.cpf = user.cpf;
        this.password = user.password;
    }

    /**
     * Retorno da view em formato de String.
     * @return O json de autenticação em formato de String.
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
