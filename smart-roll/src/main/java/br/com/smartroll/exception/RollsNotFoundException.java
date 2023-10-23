package br.com.smartroll.exception;

/**
 * Exceção lançada quando as chamadas de uma determinada turma não foram encontradas.
 */
public class RollsNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o id da chamada.
     * @param code código da turma utilizado para tentar obter chamadas que não foram encontradas.
     * @param semester semestre utilizado para tentar obter chamadas que não foram encontradas.
     */
    public RollsNotFoundException(String code, String semester){
        super("No rolls associated with class code: " + code + " and " + semester + " was found.");
    }
}
