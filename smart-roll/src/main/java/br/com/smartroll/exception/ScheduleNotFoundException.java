package br.com.smartroll.exception;

/**
 * Exceção lançada quando agendamento não foi encontrado.
 */
public class ScheduleNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o id do agendamento.
     * @param id do agendamento.
     */
    public ScheduleNotFoundException(String id){
        super("No schedule associated with id: " + id + " was found.");
    }
}
