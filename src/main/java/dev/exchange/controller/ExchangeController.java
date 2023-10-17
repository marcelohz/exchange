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

import java.util.Set;

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
        if(!Set.of(1, 2, 5, 10, 20, 50, 100).contains(exchangeRequest.getBillValue())) {
            throw new InvalidDataException("billValue must be 1, 2, 5, 10, 20, 50, 100");
        }
        if(exchangeRequest.getMaxCoins() != null && exchangeRequest.getMaxCoins() < 0) {
            throw new InvalidDataException("maxCoins has to be greater than -1");
        }
        ExchangeResponse response = exchangeService.processExchange(exchangeRequest.getBillValue(), exchangeRequest.getMaxCoins());
        return ResponseEntity.ok(response);
    }

}


