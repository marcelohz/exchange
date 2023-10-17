package dev.exchange.service;

public class ExceededMaxCoinsException extends RuntimeException {

    public static String errorCode = "MAX_COINS_EXCEEDED";

    public ExceededMaxCoinsException(String message) {
        super(message);
    }
}
