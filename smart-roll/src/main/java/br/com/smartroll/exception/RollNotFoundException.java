package br.com.smartroll.exception;

/**
 * Exceção lançada quando não foi encontrada uma chamada.
 */
public class RollNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o id da chamada.
     * @param id da chamada.
     */
    public RollNotFoundException(String id){
        super("No roll associated with id: " + id + " was found.");
    }
}
