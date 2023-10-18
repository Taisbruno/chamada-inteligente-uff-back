package br.com.smartroll.exception;

/**
 * Exceção lançada quando não foi encontrada uma chamada.
 */
public class RollNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o id da chamada.
     * @param id semestre utilizado para tentar obter chamadas referente a turmas que não foram encontradas.
     */
    public RollNotFoundException(String id){
        super("No roll associated with id: " + id + " was found.");
    }
}
