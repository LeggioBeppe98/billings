package com.bldev.billings.exception;



public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Non si dispone delle autorizzazioni necessarie.");
    }
}
