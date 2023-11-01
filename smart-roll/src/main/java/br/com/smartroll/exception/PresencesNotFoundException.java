package br.com.smartroll.exception;

/**
 * Exceção lançada quando não foram encontradas presenças associadas a uma chamada.
 */
public class PresencesNotFoundException extends Exception{

    /**
     * Construtor padrão da exceção
     * @param id id da chamada.
     */
    public PresencesNotFoundException(String id){
        super("No presences associated with roll id: " + id + " was found.");
    }
}
