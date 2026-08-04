package com.bldev.billings.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Utente con id " + id + " non trovato");
    }

}
