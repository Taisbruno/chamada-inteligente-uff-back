package br.com.smartroll.exception;

/**
 * Exceção lançada quando o tempo é inválido.
 */
public class InvalidTimeException extends Exception{
    /**
     * Construtor padrão da exceção que recebe o motivo pelo qual o tempo é inválido.
     * @param message mensagem com o motivo do tempo ser inválido.
     */
    public InvalidTimeException(String message) {
        super("Invalid Time: " + message);
    }
}
