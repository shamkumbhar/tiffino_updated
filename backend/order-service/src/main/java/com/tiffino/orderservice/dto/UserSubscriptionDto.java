package com.tiffino.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionDto {

    private Long id;
    private Long userId;
    private Long planId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private LocalDate nextRenewalDate;
    private Long deliveryAddressId;
    private String customizations;



}
