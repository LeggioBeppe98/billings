package com.bldev.billings.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String email) {
        super("Utente con email " + email + " già esistente");
    }
}
