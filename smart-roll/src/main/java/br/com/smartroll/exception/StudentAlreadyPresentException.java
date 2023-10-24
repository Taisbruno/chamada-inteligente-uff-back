package br.com.smartroll.exception;

/**
 * Exceção lançada quando um aluno tenta se inscrever em uma chamada a qual já esteja inscrito.
 */
public class StudentAlreadyPresentException extends Exception{
    /**
     * Construtor padrão da exceção.
     * @param registration matrícula do aluno.
     * @param callId id da chamada.
     */
    public StudentAlreadyPresentException(String registration, String callId){
        super("Student with registration: " + registration + " is already subscribed to the call: " + callId );
    }
}
