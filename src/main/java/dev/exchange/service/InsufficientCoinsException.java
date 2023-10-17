package dev.exchange.service;

public class InsufficientCoinsException extends RuntimeException {

    public InsufficientCoinsException(String message) {
        super(message);
    }
}
