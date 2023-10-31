package br.com.smartroll.exception;

public class FailedStudentsNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o classCode e semester da turma usado para encontrar alunos reprovados por falta.
     * @param classCode código da turma.
     * @param semester semestre da turma.
     */
    public FailedStudentsNotFoundException(String classCode, String semester){
        super("No failed studentes associated with class: " + classCode + " and " + semester + " was found.");
    }
}
