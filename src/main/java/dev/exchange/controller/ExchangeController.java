package dev.exchange.controller;

import dev.exchange.dto.ApiResponse;
import dev.exchange.dto.ErrorResponse;
import dev.exchange.dto.ExchangeRequest;
import dev.exchange.dto.ExchangeResponse;
import dev.exchange.service.ExceededMaxCoinsException;
import dev.exchange.service.ExchangeService;
import dev.exchange.service.InsufficientCoinsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping("/exchange")
    public ResponseEntity<ApiResponse<ExchangeResponse>> exchange(@RequestBody ExchangeRequest exchangeRequest) {
        if (exchangeRequest == null || !isValid(exchangeRequest)) {
            throw new InvalidJsonFormatException("Invalid JSON format. Please provide valid data.");
        }
            ExchangeResponse response = exchangeService.processExchange(exchangeRequest.getBillValue(), exchangeRequest.getMaxCoins());
            return ResponseEntity.ok(new ApiResponse<>(response));
    }

    private boolean isValid(ExchangeRequest exchangeRequest) {
        return exchangeRequest.getBillValue() > 0;
    }
}


