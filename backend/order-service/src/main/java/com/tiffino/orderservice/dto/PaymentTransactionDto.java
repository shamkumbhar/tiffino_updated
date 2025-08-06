package com.tiffino.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentTransactionDto {


    private Long id;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private String paymentMethod;
    private String transactionId;
    private String status;
    private LocalDateTime transactionDate;










}
