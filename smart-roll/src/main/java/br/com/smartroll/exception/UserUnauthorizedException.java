package br.com.smartroll.exception;


/**
 * Exceção lançada quando as credenciais do usuário são inválidas ou não existentes.
 */
public class UserUnauthorizedException extends Exception{

    /**
     * Construtor padrão da exceção.
     */
    public UserUnauthorizedException(){
        super("User has invalid credentials or doesn't exist.");
    }
}
