package dev.exchange.service;

public class InsufficientCoinsException extends RuntimeException {

    public static String errorCode = "INSUFFICIENT_COINS";

//    public InsufficientCoinsException() {
//        super("Insufficient coins to process transaction");
//    }
    public InsufficientCoinsException(String message) {
        super(message);
    }
}
