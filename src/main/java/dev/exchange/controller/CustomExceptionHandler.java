package dev.exchange.controller;

import dev.exchange.dto.ApiResponse;
import dev.exchange.dto.ErrorResponse;
import dev.exchange.service.ExceededMaxCoinsException;
import dev.exchange.service.InsufficientCoinsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidJsonFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidJsonFormat(InvalidJsonFormatException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(InsufficientCoinsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleInsufficientCoins(InsufficientCoinsException ex) {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),
                InsufficientCoinsException.errorCode);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(errorResponse);
    }
    @ExceptionHandler(ExceededMaxCoinsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleMaxCoinsExceeded(ExceededMaxCoinsException ex) {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),
                ExceededMaxCoinsException.errorCode);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(errorResponse);
    }

}