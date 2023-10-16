package dev.exchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ExchangeResponse {

    int giveCoin1;
    int giveCoin5;
    int giveCoin10;
    int giveCoin25;

    public ExchangeResponse(int coin1, int coin5, int coin10, int coin25) {
        this.giveCoin1 = coin1;
        this.giveCoin5 = coin5;
        this.giveCoin10 = coin10;
        this.giveCoin25 = coin25;
    }
}

