package com.bldev.billings.exception;

public class MetodoPagamentoAlreadyExistsException extends RuntimeException {
    public MetodoPagamentoAlreadyExistsException(String nome) {
        super("Il metodo di pagamento con nome: " + nome + " è già presente.");
    }
}
