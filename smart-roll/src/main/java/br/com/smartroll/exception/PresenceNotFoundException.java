package br.com.smartroll.exception;

/**
 * Exceção lançada quando presença não foi encontrada
 */
public class PresenceNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o id da presença.
     * @param id da presença.
     */
    public PresenceNotFoundException(String id) {
            super("Presence with id: " + id + " not found.");
    }
}
