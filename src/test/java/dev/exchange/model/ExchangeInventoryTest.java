package dev.exchange.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExchangeInventoryTest {

    @Test
    void testGetCoins() {
        ExchangeInventory exchangeInventory = new ExchangeInventory();
        exchangeInventory.setCoins1(10);
        exchangeInventory.setCoins5(20);
        exchangeInventory.setCoins10(30);
        exchangeInventory.setCoins25(40);

        assertEquals(10, exchangeInventory.getCoins(1));
        assertEquals(20, exchangeInventory.getCoins(5));
        assertEquals(30, exchangeInventory.getCoins(10));
        assertEquals(40, exchangeInventory.getCoins(25));
    }

    @Test
    void testSubtractCoins() {
        ExchangeInventory exchangeInventory = new ExchangeInventory();
        exchangeInventory.setCoins1(10);
        exchangeInventory.setCoins5(20);
        exchangeInventory.setCoins10(30);
        exchangeInventory.setCoins25(40);

        exchangeInventory.subtractCoins(1, 5);
        exchangeInventory.subtractCoins(5, 10);
        exchangeInventory.subtractCoins(10, 15);
        exchangeInventory.subtractCoins(25, 20);

        assertEquals(5, exchangeInventory.getCoins1());
        assertEquals(10, exchangeInventory.getCoins5());
        assertEquals(15, exchangeInventory.getCoins10());
        assertEquals(20, exchangeInventory.getCoins25());
    }
}