package br.com.smartroll.exception;

/**
 * Exceção lançada quando um usuário não foi encontrado.
 */
public class UserNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe a matrícula do usuário.
     * @param registration matrícula do usuário.
     */
    public UserNotFoundException(String registration){
        super("No user associated with registration: " + registration + " was found.");
    }
}
