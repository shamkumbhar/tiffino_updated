package com.tiffino.orderservice.dto;

import com.tiffino.orderservice.enumss.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusHistoryDto {

    private OrderStatus status;
    private LocalDateTime changedAt;
}
