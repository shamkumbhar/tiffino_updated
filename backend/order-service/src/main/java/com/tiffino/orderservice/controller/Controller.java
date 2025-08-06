package com.tiffino.orderservice.controller;

import com.tiffino.orderservice.dto.*;
//import com.tiffino.order.dto.OrderDto;
//import com.tiffino.order.dto.OrderStatusHistoryDto;
//import com.tiffino.order.dto.UserDto;
import com.tiffino.orderservice.entity.OrderStatusHistory;
import com.tiffino.orderservice.enumss.MealType;
import com.tiffino.orderservice.enumss.OrderStatus;
import com.tiffino.orderservice.external.Userclient;
import com.tiffino.orderservice.repository.OrderStatusHistoryRepository;
import com.tiffino.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/order")
public class Controller {

    @Autowired
    private   OrderService orderService;

  @Autowired
  private Userclient userclient;

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;


    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> create( @Valid @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }


    @GetMapping("/getall")
    public List<OrderDto> getAllOrders() {
        return orderService.getallorders();
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order with ID " + id + " has been deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto dto) {
        return ResponseEntity.ok(orderService.updateOrder(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
//PATCH http://13.200.6.63:8081/order/5/status?status=DELIVERED

   @GetMapping("/user/{userId}/history")
    public List<OrderDto> getOrderHistory(@PathVariable Long userId) {
        return orderService.getOrderHistoryByUserId(userId);
   }

//GET http://13.200.6.63:8081/order/user/15/history

    @GetMapping("/orders/{orderId}/status-history")
    public ResponseEntity<List<OrderStatusHistoryDto>> getOrderStatusHistory(@PathVariable Long orderId) {
        List<OrderStatusHistory> history = orderStatusHistoryRepository.findByOrderIdOrderByChangedAtAsc(orderId);
        List<OrderStatusHistoryDto> response = history.stream()
                .map(h -> OrderStatusHistoryDto.builder()
                        .status(h.getStatus())
                        .changedAt(h.getChangedAt())
                        .build())
                .toList();
        return ResponseEntity.ok(response);
    }




    @GetMapping("/users/by-subscription")
    public ResponseEntity<List<UserDto>> getUsersBySubscription(
            @RequestParam boolean isSubscription) {
        return ResponseEntity.ok(orderService.getUsersBySubscriptionType(isSubscription));
    }//http://13.200.6.63:8081/order/users/by-subscription?isSubscription=true




    @GetMapping("/by-date-and-meal")
    public ResponseEntity<List<OrderDto>> getOrdersByDateAndMeal(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("mealType") MealType mealType) {
        List<OrderDto> result = orderService.getOrdersByDateAndMeal(date, mealType);
        return ResponseEntity.ok(result);
    }
//http://13.200.6.63:8081/order/by-date-and-meal?date=2025-06-23&mealType=BREAKFAST


    @GetMapping("/by-date")
    public ResponseEntity<List<OrderDto>> getOrdersByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<OrderDto> orders = orderService.getOrdersByDate(date);
        return ResponseEntity.ok(orders);
    }

//http://13.200.6.63:8081/order/by-date?date=2025-06-23



    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDto> cancelOrder(
            @PathVariable Long orderId,
            @RequestBody CancelOrderRequest request) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId, request));
    }



    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus1(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }


}
