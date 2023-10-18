package br.com.smartroll.exception;

/**
 * Classe responsável por representar uma exceção lançada quando um usuário não está autorizado para realizar a requisição.
 */
public class UserNotAuthorizedException extends Exception {

    /**
     * Construtor padrão da exceção.
     */
    public UserNotAuthorizedException(){
        super("User is not authorized to access this resource.");
    }
}
