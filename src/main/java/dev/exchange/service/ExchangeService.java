package dev.exchange.service;

import dev.exchange.model.ExchangeInventory;
import dev.exchange.dto.ExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ExchangeService {

    private final ExchangeInventory exchangeInventory;

    @Autowired
    public ExchangeService(ExchangeInventory exchangeInventory) {
        this.exchangeInventory = exchangeInventory;
    }

    public ExchangeResponse processExchange(int billValue) throws InsufficientCoinsException, ExceededMaxCoinsException {
        return processExchange(billValue, null);
    }

    public synchronized ExchangeResponse processExchange(int billValue, Integer maxCoins) throws InsufficientCoinsException, ExceededMaxCoinsException {
        int billAmountCents = billValue * 100;

        int[] coinValues = {25, 10, 5, 1};
        int[] coinsNeeded = new int[4];
        int totalCents = 0;

        for (int i = 0; i < coinValues.length; i++) {
            if(totalCents == billAmountCents) break;

            coinsNeeded[i] = (billAmountCents - totalCents) / coinValues[i];
            if (coinsNeeded[i] > exchangeInventory.getCoins(coinValues[i])) {
                coinsNeeded[i] = exchangeInventory.getCoins(coinValues[i]);
            }
            totalCents += coinsNeeded[i] * coinValues[i];
        }

        if (totalCents < billAmountCents) {
            throw new InsufficientCoinsException("Insufficient coins to perform exchange");
        }

        if (maxCoins != null && Arrays.stream(coinsNeeded).sum() > maxCoins) {
            throw new ExceededMaxCoinsException("Would need more coins for bill");
        }

        for (int i = 0; i < coinValues.length; i++) {
            exchangeInventory.subtractCoins(coinValues[i], coinsNeeded[i]);
        }

        return new ExchangeResponse(coinsNeeded[3], coinsNeeded[2], coinsNeeded[1], coinsNeeded[0]);
    }
}

