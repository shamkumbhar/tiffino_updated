package com.tiffino.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {


    private Long id;

    private Long orderId;

    private Long mealId;

    private Integer quantity;

    private String customizations;

    private BigDecimal pricePerItem;








}
