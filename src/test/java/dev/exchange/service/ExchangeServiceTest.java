package dev.exchange.service;

import dev.exchange.dto.ExchangeResponse;
import dev.exchange.model.ExchangeInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class ExchangeServiceTest {

    @Mock
    private ExchangeInventory exchangeInventory;

    @InjectMocks
    private ExchangeService exchangeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(exchangeInventory.getCoins(anyInt())).thenReturn(100);
    }
    @Test
    void testSingleExchange() {

        ExchangeResponse response;
        response = exchangeService.processExchange(20);
        assertEquals(80, response.getGiveCoin25());

    }

    @Test
    void testSingleExchangeExceedingMaxCoins() {
        assertThrows(ExceededMaxCoinsException.class, () ->
                exchangeService.processExchange(20, 10));
    }

    @Test
    void testSingleExchangeNotExceedingMaxCoins() {
        ExchangeResponse response;
        response = exchangeService.processExchange(20, 100);
        assertEquals(80, response.getGiveCoin25());

    }


    @Test
    void testInsufficientCoins()  {
        assertThrows(InsufficientCoinsException.class, () ->
            exchangeService.processExchange(1000)
        );
    }
    @Test
    void testMultipleExchangesUntilInsufficient() {

        ExchangeResponse response;
        response = exchangeService.processExchange(20);
        assertEquals(80, response.getGiveCoin25());
        when(exchangeInventory.getCoins(25)).thenReturn(20);

        response = exchangeService.processExchange(10);
        assertEquals(20, response.getGiveCoin25());
        assertEquals(50, response.getGiveCoin10());
        when(exchangeInventory.getCoins(25)).thenReturn(0);
        when(exchangeInventory.getCoins(10)).thenReturn(50);

        response = exchangeService.processExchange(5);
        assertEquals(50, response.getGiveCoin10());
        when(exchangeInventory.getCoins(10)).thenReturn(0);

        response = exchangeService.processExchange(5);
        assertEquals(100, response.getGiveCoin5());
        when(exchangeInventory.getCoins(5)).thenReturn(0);

        response = exchangeService.processExchange(1);
        assertEquals(100, response.getGiveCoin1());
        when(exchangeInventory.getCoins(1)).thenReturn(0);

        assertThrows(InsufficientCoinsException.class, () ->
            exchangeService.processExchange(1)
        );
    }
}
