package dev.exchange.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ExchangeInventory {

    @Value("${coin.starting-amount.1}")
    int coins1;
    @Value("${coin.starting-amount.5}")
    int coins5;
    @Value("${coin.starting-amount.10}")
    int coins10;
    @Value("${coin.starting-amount.25}")
    int coins25;

    /**
     * @param type which coin to get
     * @return number of coins in inventory
     */
    public int getCoins(int type) {
        switch (type) {
            case 1:
                return coins1;
            case 5:
                return coins5;
            case 10:
                return coins10;
            case 25:
                return coins25;
        }
        throw new RuntimeException("asked an ExchangeInventory for invalid coin: " + type);
    }

    /**
     * @param coinValue which coin to subtract
     * @param toSubtract how much to subtract
     */
    public void subtractCoins(int coinValue, int toSubtract) {
        switch (coinValue) {
            case 1:
                setCoins1(getCoins1() - toSubtract);
                break;
            case 5:
                setCoins5(getCoins5() - toSubtract);
                break;
            case 10:
                setCoins10(getCoins10() - toSubtract);
                break;
            case 25:
                setCoins25(getCoins25() - toSubtract);
                break;
        }
    }
}