package br.com.smartroll.exception;

/**
 * Representa uma exceção que diz que o Json está com formato incorreto ou fora da padronização esperada.
 */
public class InvalidJsonException extends Exception {

    /**
     * Construtor padrão da exceção que deve receber o motivo pelo qual o Json é inválido ou se este está fora de padrão..
     */
    public InvalidJsonException(String text) {
        super("Invalid Json: " + text);
    }
}
