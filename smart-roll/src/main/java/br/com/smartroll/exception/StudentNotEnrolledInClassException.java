package br.com.smartroll.exception;

/**
 * Exceção lançada quando um aluno tenta se inscrever em uma chamada de uma turma a qual não está inscrito.
 */
public class StudentNotEnrolledInClassException extends Exception{

    /**
     * Construtor padrão da exceção.
     * @param registration matrícula do aluno.
     * @param codeClass código da turma.
     */
    public StudentNotEnrolledInClassException(String registration, String codeClass){
        super("Student with registration: " + registration + " is not enrolled in the class: " + codeClass );
    }
}
