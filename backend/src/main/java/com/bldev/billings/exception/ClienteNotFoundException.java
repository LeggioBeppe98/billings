package com.bldev.billings.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(Long id) {
        super("Il cliente con id " + id + " non è presente.");
    }

    public ClienteNotFoundException(String codiceFiscale) {
        super("Il cliente con Codice fiscale " + codiceFiscale + " non è presente.");
    }
}
