package dev.exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.exchange.dto.ExchangeRequest;
import dev.exchange.model.ExchangeInventory;
import dev.exchange.service.ExceededMaxCoinsException;
import dev.exchange.service.InsufficientCoinsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExchangeInventory exchangeInventory;

    @BeforeEach
    public void setUp() {
        exchangeInventory.setCoins25(100);
        exchangeInventory.setCoins10(100);
        exchangeInventory.setCoins5(100);
        exchangeInventory.setCoins1(100);
    }

    @Test
    public void testExchangeApi() throws Exception {
        performExchangeRequestAndVerify(20, 80, 0, 0, 0);
    }

    @Test
    public void testExceedingMaxCoins() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ExchangeRequest(10, 1))))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.code").value(ExceededMaxCoinsException.code));

    }

    @Test
    public void testInsufficientCoins() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ExchangeRequest(1000))))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.code").value(InsufficientCoinsException.code));
    }

    @Test
    public void testMultipleExchangesUntilInsufficient() throws Exception {
        performExchangeRequestAndVerify(20, 80, 0, 0, 0);
        performExchangeRequestAndVerify(10, 20, 50, 0, 0);
        performExchangeRequestAndVerify(5, 0, 50, 0, 0);
        performExchangeRequestAndVerify(5, 0, 0, 100, 0);
        performExchangeRequestAndVerify(1, 0, 0, 0, 100);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ExchangeRequest(1))))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.code").value(InsufficientCoinsException.code));
    }

    private void performExchangeRequestAndVerify(int billValue, int giveCoin25, int giveCoin10, int giveCoin5, int giveCoin1) throws Exception {
        ExchangeRequest exchangeRequest = new ExchangeRequest(billValue, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exchangeRequest)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coins.giveCoin25").value(giveCoin25))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coins.giveCoin10").value(giveCoin10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coins.giveCoin5").value(giveCoin5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coins.giveCoin1").value(giveCoin1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

