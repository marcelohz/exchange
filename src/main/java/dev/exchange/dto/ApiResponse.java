package dev.exchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ApiResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T coins;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorResponse error;

    public ApiResponse(T coins) {
        this.error = null;
        this.coins = coins;
    }

    public ApiResponse(ErrorResponse error) {
        this.error = error;
    }
}
