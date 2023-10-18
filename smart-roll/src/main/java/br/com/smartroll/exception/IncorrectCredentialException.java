package br.com.smartroll.exception;

/**
 * Exceção lançada quando credenciais do usuário estão incorretas.
 */
public class IncorrectCredentialException extends Exception{
    /**
     * Construtor padrão da exceção.
     */
    public IncorrectCredentialException() {
        super("Incorrect credential: incorrect cpf or password");
    }
}
