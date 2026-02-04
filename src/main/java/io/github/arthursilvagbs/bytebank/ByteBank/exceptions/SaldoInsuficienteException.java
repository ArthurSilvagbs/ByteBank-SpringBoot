package io.github.arthursilvagbs.bytebank.ByteBank.exceptions;

public class SaldoInsuficienteException extends RuntimeException{
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
