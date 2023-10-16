package dev.exchange.service;

public class InsufficientCoinsException extends Exception {

    public static String code = "INSUFFICIENT_COINS";

    public InsufficientCoinsException() {
        super("Insufficient coins to process transaction");
    }
    public InsufficientCoinsException(String message) {
        super(message);
    }
}
