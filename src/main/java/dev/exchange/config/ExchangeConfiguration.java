//package dev.exchange.config;
//
//import dev.exchange.model.ExchangeInventory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ExchangeConfiguration {
//
//    @Value("${coin.starting-amount.1}")
//    private int startingAmountFor1Coin;
//
//    @Value("${coin.starting-amount.5}")
//    private int startingAmountFor5Coin;
//
//    @Value("${coin.starting-amount.10}")
//    private int startingAmountFor10Coin;
//
//    @Value("${coin.starting-amount.25}")
//    private int startingAmountFor25Coin;
//
//
//    @Bean
//    public ExchangeInventory exchangeInventory() {
//        return new ExchangeInventory(startingAmountFor1Coin, startingAmountFor5Coin,
//                startingAmountFor10Coin, startingAmountFor25Coin);
//    }
//}
