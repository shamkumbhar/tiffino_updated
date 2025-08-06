package com.tiffino.orderservice.entity;


import com.tiffino.orderservice.enumss.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @Column(name = "usersubscription_id")
    private Long usersubscriptionId;


    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;


    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;


    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;


    @Column(name = "delivery_adress_id", nullable = false)
    private Long deliveryAddressId;


    @Column(name = "payment_id")
    private Long paymentId;


    @Column(name = "notes")
    private String notes;

    @Column(name = "applied_discount")
    private BigDecimal appliedDiscount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> items;


    @Column(name = "delivery_time_slot")
    private  String deliveryTimeSlot;


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type")
    private List<MealType> mealTypes;



    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;  // DAILY, MONTHLY, etc.

    private boolean isSubscription;  // true = subscription, false = onetime


    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;



    public void setIsSubscription(Boolean isSubscription) {
        this.isSubscription = isSubscription;
    }


    public Boolean getIsSubscription() {
        return isSubscription;
    }
}