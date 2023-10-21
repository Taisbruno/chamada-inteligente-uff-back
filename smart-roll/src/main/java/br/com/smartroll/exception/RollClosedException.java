package br.com.smartroll.exception;

/**
 * Exceção lançada quando uma chamada já está fechada e não aceita mais submissões de presença.
 */
public class RollClosedException extends Exception{
    /**
     * Construtor padrão da exceção que deve receber o motivo pelo qual o Json é inválido ou se este está fora de padrão..
     */
    public RollClosedException(String id) {
        super("Roll with id: " + id + " is closed.");
    }
}
