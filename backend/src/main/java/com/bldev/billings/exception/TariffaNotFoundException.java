package com.bldev.billings.exception;

public class TariffaNotFoundException extends RuntimeException {
  public TariffaNotFoundException(String message) {
    super(message);
  }
}
