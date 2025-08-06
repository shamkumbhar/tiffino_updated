package com.tiffino.orderservice.repository;

import com.tiffino.orderservice.entity.Order;
import com.tiffino.orderservice.enumss.*;
//import com.Tiffino.order.enumss.OrderStatus;
//import com.Tiffino.order.enumss.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items WHERE o.userId = :userId ORDER BY o.orderDate DESC")
    List<Order> findByUserIdWithItems(@Param("userId") Long userId);


    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);



    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items")
    List<Order> findAllWithItems();

    @Query("SELECT DISTINCT o.userId FROM Order o WHERE o.isSubscription = :isSubscription")
    List<Long> findUserIdsBySubscriptionType(@Param("isSubscription") boolean isSubscription);

    List<Order> findByDeliveryDate(LocalDate deliveryDate);

    @Query("SELECT DISTINCT o.userId FROM Order o WHERE o.orderType = :orderType")
    List<Long> findDistinctUserIdsByOrderType(@Param("orderType") OrderType orderType);


    @Query("SELECT o FROM Order o JOIN o.mealTypes m WHERE o.deliveryDate = :date AND m = :mealType")
    List<Order> findByDeliveryDateAndMealType(@Param("date") LocalDate date, @Param("mealType") MealType mealType);





    List<Order> findByStatus(OrderStatus status);
}
