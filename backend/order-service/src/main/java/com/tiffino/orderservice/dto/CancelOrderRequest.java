package com.tiffino.orderservice.dto;

import lombok.Data;

@Data
public class CancelOrderRequest {

    private Long userId;
    private String reason;
}
