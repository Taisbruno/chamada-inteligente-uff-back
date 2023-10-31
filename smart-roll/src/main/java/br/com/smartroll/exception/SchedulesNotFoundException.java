package br.com.smartroll.exception;

/**
 * Exceção lançada quando não foram encontrados agendamentos associados a uma turma.
 */
public class SchedulesNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o id da chamada.
     * @param code código da turma utilizado para tentar obter agendamentos que não foram encontradas.
     */
    public SchedulesNotFoundException(String code){
        super("No schedules associated with class code: " + code  + " was found.");
    }
}
