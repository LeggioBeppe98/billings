package com.bldev.billings.exception;

public class TariffaNotFoundException extends RuntimeException {
    public TariffaNotFoundException(Long id) {
        super("Tariffa con id " + id + " non trovata");
    }
}
