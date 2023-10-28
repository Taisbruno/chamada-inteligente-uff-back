package br.com.smartroll.exception;

public class ClassroomNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o id da turma.
     * @param id da turma.
     */
    public ClassroomNotFoundException(String id){
        super("No class associated with id: " + id + " was found.");
    }
}
