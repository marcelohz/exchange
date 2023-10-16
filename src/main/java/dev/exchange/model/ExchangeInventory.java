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

    public int getCoins(int value) {
        switch (value) {
            case 1:
                return coins1;
            case 5:
                return coins5;
            case 10:
                return coins10;
            case 25:
                return coins25;
        }
        throw new RuntimeException("asked an ExchangeInventory for invalid coin: " + value);
    }

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

//    public void resetToDefault() {
//        this.coins1 = defaultCoins1;
//        this.coins5 = defaultCoins5;
//        this.coins10 = defaultCoins10;
//        this.coins25 = defaultCoins25;
//    }

}