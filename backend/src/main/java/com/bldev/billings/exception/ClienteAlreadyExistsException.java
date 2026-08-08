package com.bldev.billings.exception;

public class ClienteAlreadyExistsException extends RuntimeException {
    public ClienteAlreadyExistsException(String codiceFiscale) {
        super("Il cliente con codice fiscale " + codiceFiscale + " è già presente.");
    }
}
