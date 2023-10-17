package dev.exchange.controller;

import dev.exchange.dto.ExchangeRequest;
import dev.exchange.dto.ExchangeResponse;
import dev.exchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }


    @PostMapping("/exchange")
    public ResponseEntity<ExchangeResponse> exchange(@RequestBody ExchangeRequest exchangeRequest) {
        if(exchangeRequest.getBillValue() < 0) {
            throw new InvalidDataException("billValue has to be greater than -1");
        }
        if(exchangeRequest.getMaxCoins() != null && exchangeRequest.getMaxCoins() < 0) {
            throw new InvalidDataException("maxCoins has to be greater than -1");
        }
        ExchangeResponse response = exchangeService.processExchange(exchangeRequest.getBillValue(), exchangeRequest.getMaxCoins());
        return ResponseEntity.ok(response);
    }
}


