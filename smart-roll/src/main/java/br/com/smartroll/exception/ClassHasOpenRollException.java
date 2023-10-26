package br.com.smartroll.exception;

/**
 * Exceção lançada quando se tenta abrir uma chamada mas a turma já contém uma chamada aberta.
 */
public class ClassHasOpenRollException extends Exception{

    /**
     * Construtor padrão que recebe o id da turma a qual houve tentativa de criação de uma nova turma.
     */
    public ClassHasOpenRollException(String id) {
        super("The class with id: " + id + " already has an open roll. Try closing the last roll to open a new.");
    }
}
