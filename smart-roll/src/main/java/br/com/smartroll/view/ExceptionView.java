package br.com.smartroll.view;

import com.google.gson.Gson;

/**
 * Classe responsável por implementar a View de uma mensagem de exceção.
 * @author Natália
 */
public class ExceptionView {

    private final int code;
    private final String status;
    private final String message;

    /**
     * Construtor padrão da view de exceções.
     * @param code Código da mensagem http.
     * @param status O estado em forma de String.
     * @param message A mensagem customizada da exceção.
     */
    public ExceptionView(int code, String status, String message){
        this.code = code;
        this.status = status;
        this.message = message;
    }

    /**
     * Método responsável por retornar um Json serializado de uma mensagem de exceção.
     * @return A resposta em formato de Json.
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
