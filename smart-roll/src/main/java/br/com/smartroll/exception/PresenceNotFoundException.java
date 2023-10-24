package br.com.smartroll.exception;

public class PresenceNotFoundException extends Exception{

    public PresenceNotFoundException(String id) {
            super("Presence with id: " + id + " not found.");
        }
}
