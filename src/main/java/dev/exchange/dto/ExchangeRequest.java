package dev.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequest {
    private int billValue;
    private Integer maxCoins;

    public ExchangeRequest(int billValue) {
        this.billValue = billValue;
        this.maxCoins = null;
    }
//    public ExchangeRequest(int billValue, Integer maxCoins) {
//        this.billValue = billValue;
//        this.maxCoins = maxCoins;
//    }
}

