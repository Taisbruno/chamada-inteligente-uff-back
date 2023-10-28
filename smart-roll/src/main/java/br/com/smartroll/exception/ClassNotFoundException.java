package br.com.smartroll.exception;

public class ClassNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o id da turma.
     * @param id da turma.
     */
    public ClassNotFoundException(String id){
        super("No class associated with id: " + id + " was found.");
    }
}
