package dev.exchange.service;

public class ExceededMaxCoinsException extends RuntimeException {

    public ExceededMaxCoinsException(String message) {
        super(message);
    }
}
