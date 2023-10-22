package br.com.smartroll.exception;

public class PresenceNotFoundException extends RuntimeException {
    public PresenceNotFoundException(String message) {
        super(message);
    }
}
