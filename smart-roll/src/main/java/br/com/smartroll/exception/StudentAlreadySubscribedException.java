package br.com.smartroll.exception;

/**
 * Exceção lançada quando um aluno tenta se inscrever em uma chamada a qual já esteja inscrito.
 */
public class StudentAlreadySubscribedException extends Exception{
    /**
     * Construtor padrão da exceção.
     * @param registration matrícula do aluno.
     * @param callId id da chamada.
     */
    public StudentAlreadySubscribedException(String registration, String callId){
        super("Student with registration: " + registration + " is already subscribed to the call: " + callId );
    }
}
