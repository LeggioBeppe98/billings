package com.bldev.billings.exception;

public class MetodoPagamentoNotFoundException extends RuntimeException {
    public MetodoPagamentoNotFoundException(Long id) {
        super("Il metodo di pagamento con id: " + id + " non è presente.");
    }
}
