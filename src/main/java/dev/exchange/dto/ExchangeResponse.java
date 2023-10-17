package dev.exchange.dto;

import lombok.Data;

@Data
//this will be the json returned, with the quantity of each coin
public class ExchangeResponse {

    int giveCoin1;
    int giveCoin5;
    int giveCoin10;
    int giveCoin25;

    /**
     * @param coin1 quantity of 1 cent coins
     * @param coin5 quantity of 5 cent coins
     * @param coin10 quantity of 10 cent coins
     * @param coin25 quantity of 25 cent coins
     */
    public ExchangeResponse(int coin1, int coin5, int coin10, int coin25) {
        this.giveCoin1 = coin1;
        this.giveCoin5 = coin5;
        this.giveCoin10 = coin10;
        this.giveCoin25 = coin25;
    }
}

