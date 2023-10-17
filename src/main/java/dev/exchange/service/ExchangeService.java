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

    /**
     * @param billValue value, in dollars, of the bill to be exchanged
     * @return ExchangeResponse with all the coins
     */
    public ExchangeResponse processExchange(int billValue) throws InsufficientCoinsException, ExceededMaxCoinsException {
        return processExchange(billValue, null);
    }

    /**
     * @param billValue value, in dollars, of the bill to be exchanged
     * @param maxCoins maximum number of coins the user is willing to accept
     * @return ExchangeResponse with all the coins
     */
    public synchronized ExchangeResponse processExchange(int billValue, Integer maxCoins) throws InsufficientCoinsException, ExceededMaxCoinsException {
        int billAmountCents = billValue * 100;

        int[] coinValues = {25, 10, 5, 1};
        int[] coinsNeeded = new int[4]; //number of each coin we need to take
        int totalCents = 0; //the total value we took from inventory

        //for each value of coin in the inventory, starting at higher values (25 down to 1)
        for (int i = 0; i < coinValues.length; i++) {
            if(totalCents == billAmountCents) break;

            coinsNeeded[i] = (billAmountCents - totalCents) / coinValues[i];
            if (coinsNeeded[i] > exchangeInventory.getCoins(coinValues[i])) {
                //didn't have enough, so take everything
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

        //subtract the needed coins from inventory
        for (int i = 0; i < coinValues.length; i++) {
            exchangeInventory.subtractCoins(coinValues[i], coinsNeeded[i]);
        }

        return new ExchangeResponse(coinsNeeded[3], coinsNeeded[2], coinsNeeded[1], coinsNeeded[0]);
    }
}

