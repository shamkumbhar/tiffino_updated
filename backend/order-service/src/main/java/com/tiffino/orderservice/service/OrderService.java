package com.tiffino.orderservice.service;

import com.tiffino.orderservice.dto.*;
//import com.tiffino.order.dto.OrderDto;
//import com.tiffino.order.dto.UserDto;
import com.tiffino.orderservice.enumss.*;
//import com.tiffino.order.enumss.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {


    OrderDto createOrder(OrderDto orderDto);

    OrderDto getOrderById(Long id);

    List<OrderDto> getallorders();

   void deleteOrder(Long id);

    OrderDto updateOrder(Long id, OrderDto orderDto);

    OrderDto updateOrderStatus(Long id, OrderStatus status);

    List<OrderDto> getOrderHistoryByUserId(Long userId);


    List<UserDto> getUsersBySubscriptionType(boolean isSubscription);



    List<OrderDto> getOrdersByDateAndMeal(LocalDate date, MealType mealType);


    List<OrderDto> getOrdersByDate(LocalDate date);


    OrderDto cancelOrder(Long orderId, CancelOrderRequest request);

    List<OrderDto> getOrdersByStatus(OrderStatus status);




}