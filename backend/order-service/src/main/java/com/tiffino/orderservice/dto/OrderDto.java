package com.tiffino.orderservice.dto;

import com.tiffino.orderservice.enumss.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {


    private Long id;


    private Long userId;

    @NotNull(message = "orderType is required")
    private OrderType orderType;


    private Long  usersubscriptionId;

    @NotNull(message = "orderDate is required")
    private LocalDateTime orderDate;

    private LocalDate deliveryDate;

    @NotNull(message = "deliveryDate is required")
    private String deliveryTimeSlot;

    @NotNull(message = "totalAmount is required")
    private BigDecimal totalAmount;

    @NotNull(message = "status is required")
    private OrderStatus status;

    private Long deliveryAddressId;

    @NotNull(message = "deliveryAddress or deliveryAddressId is required")

    private AddressDto deliveryAddress;


    private Long paymentId;

    private String notes;

    private BigDecimal appliedDiscount;

    private List<OrderItemDto> orderItems;

    private UserDto user;


    private List<MealType> mealTypes;


    private Boolean isSubscription;

    private SubscriptionType subscriptionType;

    private String cancellationReason;
    private LocalDateTime cancelledAt;


}
