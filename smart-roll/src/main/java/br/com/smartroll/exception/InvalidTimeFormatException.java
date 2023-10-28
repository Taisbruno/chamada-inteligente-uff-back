package br.com.smartroll.exception;

/**
 * Exceção lançada quando o formato do tempo é inválido.
 */
public class InvalidTimeFormatException extends Exception{
    /**
     * Construtor padrão da exceção que recebe a mensagem com o motivo pelo qual o formato do tempo é inválido.
     * @param message mensagem com o motivo do formato do tempo ser inválido.
     */
    public InvalidTimeFormatException(String message) {
        super("Invalid Time format: " + message);
    }
}
