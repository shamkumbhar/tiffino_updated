package com.tiffino.orderservice.service;

import com.tiffino.orderservice.dto.*;
import com.tiffino.orderservice.entity.*;
//import com.Tiffino.order.entity.Order;
//import com.Tiffino.order.entity.OrderItem;
//import com.Tiffino.order.entity.OrderStatusHistory;
import com.tiffino.orderservice.enumss.*;
//import com.Tiffino.order.enumss.OrderStatus;
//import com.Tiffino.order.enumss.OrderType;
import com.tiffino.orderservice.exception.OrderNotFoundException;
import com.tiffino.orderservice.external.Userclient;
import com.tiffino.orderservice.repository.*;
//import com.Tiffino.order.repository.OrderRepository;
//import com.Tiffino.order.repository.OrderStatusHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
   @Autowired
   private OrderRepository orderRepository;

@Autowired
private Userclient userclient;

@Autowired
private AddressRepository addressRepository;

@Autowired
private OrderStatusHistoryRepository orderStatusHistoryRepository;


    @Override //check and save add
    public OrderDto createOrder(OrderDto dto) {

        if (dto.getDeliveryAddress() != null) {     // orderdto  contain a address not null
            Address address = new Address();        // create new address entity object save db
            BeanUtils.copyProperties(dto.getDeliveryAddress(), address);//copies  all fields from addressdto into address entity because
            // save add entity object in db
            Address savedAddress = addressRepository.save(address); // save new address in db


            dto.setDeliveryAddressId(savedAddress.getId()); // new address id and set in orderdto
        }


        //  Create and save order
        Order order = maptoEntity(dto);
        Order savedOrder = orderRepository.save(order);
        return mapToDto(savedOrder);
    }


    @Transactional
    @Override
    public OrderDto getOrderById(Long id) {
        Order order=orderRepository.findById(id).orElseThrow(()
                        ->new OrderNotFoundException("order not with id"+id)

                );

        return mapToDto(order) ;
    }

    @Override
    public List<OrderDto> getallorders() {
        List<Order> orders = orderRepository.findAllWithItems();
        orders.forEach(o -> System.out.println("Fetched Order ID: " + o.getId()));
        return orders.stream().map(this::mapToDto).collect(Collectors.toList());
    }



    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID " + id));

        // Optionally update address if present
        if (orderDto.getDeliveryAddress() != null) {
            Address address = new Address();
            BeanUtils.copyProperties(orderDto.getDeliveryAddress(), address);
            Address savedAddress = addressRepository.save(address);
            orderDto.setDeliveryAddressId(savedAddress.getId());
        }

        // Update fields
        existingOrder.setUserId(orderDto.getUserId());
        existingOrder.setOrderType(orderDto.getOrderType());
        existingOrder.setUsersubscriptionId(orderDto.getUsersubscriptionId());
        existingOrder.setOrderDate(orderDto.getOrderDate());
        existingOrder.setDeliveryDate(orderDto.getDeliveryDate());

        existingOrder.setTotalAmount(orderDto.getTotalAmount());
        existingOrder.setStatus(orderDto.getStatus());
        existingOrder.setDeliveryAddressId(orderDto.getDeliveryAddressId());
        existingOrder.setPaymentId(orderDto.getPaymentId());
        existingOrder.setNotes(orderDto.getNotes());
        existingOrder.setAppliedDiscount(orderDto.getAppliedDiscount());

        if (orderDto.getSubscriptionType() != null) {
            existingOrder.setSubscriptionType(orderDto.getSubscriptionType());
        }
        if (orderDto.getIsSubscription() != null) {
            existingOrder.setIsSubscription(orderDto.getIsSubscription());
        }

        if (orderDto.getIsSubscription() != null) {
            existingOrder.setIsSubscription(orderDto.getIsSubscription());
        }



        // Update order items
        if (orderDto.getOrderItems() != null) {
            List<OrderItem> items = orderDto.getOrderItems().stream().map(itemDto -> OrderItem.builder()
                    .id(itemDto.getId())
                    .mealId(itemDto.getMealId())
                    .quantity(itemDto.getQuantity())
                    .customization(itemDto.getCustomizations())
                    .priceperitem(itemDto.getPricePerItem())
                    .order(existingOrder)
                    .build()).collect(Collectors.toList());
            existingOrder.setItems(items);
        }

        // Save updated order
        Order updatedOrder = orderRepository.save(existingOrder);
        return mapToDto(updatedOrder);

    }



    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + id));

        // Only log history if status is different
        if (!order.getStatus().equals(status)) {
            //  Save status change to history
            OrderStatusHistory history = OrderStatusHistory.builder()
                    .order(order)
                    .status(status)
                    .changedAt(LocalDateTime.now())

                    .build();
            orderStatusHistoryRepository.save(history);
        }

        // Update current status in order
        order.setStatus(status);
        return mapToDto(orderRepository.save(order));
    }




    @Override
    public List<OrderDto> getOrderHistoryByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserIdWithItems(userId);
        return orders.stream().map(this::mapToDto).collect(Collectors.toList());
    }


    //dto -> entity
    private Order maptoEntity(OrderDto dto)
    {
        Order order=Order.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .orderType(dto.getOrderType())
                .usersubscriptionId(dto.getUsersubscriptionId())
                .mealTypes(dto.getMealTypes())
                .subscriptionType(dto.getSubscriptionType())

                .isSubscription(dto.getIsSubscription())
                .orderDate(dto.getOrderDate())
                .deliveryDate(dto.getDeliveryDate())
                .deliveryTimeSlot(dto.getDeliveryTimeSlot())
                .totalAmount(dto.getTotalAmount())
                .status(dto.getStatus())
                .deliveryAddressId(dto.getDeliveryAddressId())
                .paymentId(dto.getPaymentId())
                .notes(dto.getNotes())
                .appliedDiscount(dto.getAppliedDiscount())

                .build();

if(dto.getOrderItems() !=null) {

    List<OrderItem> items = dto.getOrderItems().stream().map(itemDto -> OrderItem.builder()
            .id(itemDto.getId())
            .mealId(itemDto.getMealId())
            .quantity(itemDto.getQuantity())
            .customization(itemDto.getCustomizations())

            .priceperitem(itemDto.getPricePerItem())
            .order(order).build()).collect(Collectors.toList());

    order.setItems(items);
}


return order;

    }


    private OrderDto mapToDto(Order order) {
        List<OrderItemDto> itemDtos = new ArrayList<>();
        if (order.getItems() != null) {
            itemDtos = order.getItems().stream()
                    .filter(Objects::nonNull)
                    .map(item -> OrderItemDto.builder()

                            .id(item.getId())
                            .mealId(item.getMealId())
                            .quantity(item.getQuantity())
                            .customizations(item.getCustomization())
                            .pricePerItem(item.getPriceperitem())
                            .build())
                    .collect(Collectors.toList());
        }

        UserDto userDto = null;
        try {
            userDto = userclient.getUserById(order.getUserId());
        } catch (Exception e) {
            System.out.println("Failed to fetch user details: " + e.getMessage());

        }

        AddressDto addressDto = null;
        try {
            Address address = addressRepository.findById(order.getDeliveryAddressId())
                    .orElseThrow(() -> new RuntimeException("Address not found with id " + order.getDeliveryAddressId()));
            addressDto = new AddressDto();
            BeanUtils.copyProperties(address, addressDto);

            // Add debug print here
            System.out.println("AddressDto fetched: " + addressDto);

        } catch (Exception e) {
            System.out.println("Failed to fetch address details: " + e.getMessage());
        }

            return OrderDto.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .orderType(order.getOrderType())
                    .usersubscriptionId(order.getUsersubscriptionId())
                    .mealTypes(order.getMealTypes())
                    .subscriptionType(order.getSubscriptionType())
                    .isSubscription(order.getIsSubscription())

                    .orderDate(order.getOrderDate())
                    .deliveryDate(order.getDeliveryDate())
                    .deliveryTimeSlot(order.getDeliveryTimeSlot())
                    .totalAmount(order.getTotalAmount())
                    .status(order.getStatus())
                    .deliveryAddress(addressDto)
                    .paymentId(order.getPaymentId())
                    .notes(order.getNotes())
                    .appliedDiscount(order.getAppliedDiscount())
                    .orderItems(itemDtos)
                    .cancellationReason(order.getCancellationReason())
                    .cancelledAt(order.getCancelledAt())

                    .user(userDto)


                    .build();
        }


    @Override
    public List<UserDto> getUsersBySubscriptionType(boolean isSubscription) {
        OrderType type = isSubscription ? OrderType.SUBSCRIPTION : OrderType.ONE_TIME;
        List<Long> userIds = orderRepository.findDistinctUserIdsByOrderType(type);

        return userIds.stream().map(userclient::getUserById).collect(Collectors.toList());
    }



    @Override
    public List<OrderDto> getOrdersByDateAndMeal(LocalDate date, MealType mealType) {
        List<Order> orders = orderRepository.findByDeliveryDateAndMealType(date, mealType);
        return orders.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByDate(LocalDate date) {
        List<Order> orders = orderRepository.findByDeliveryDate(date);
        return orders.stream().map(this::mapToDto).collect(Collectors.toList());
    }



    @Override
    @Transactional
    public OrderDto cancelOrder(Long orderId, CancelOrderRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order is already cancelled.");
        }

        // Set cancel status, reason, and time
        order.setStatus(OrderStatus.CANCELLED);
        order.setCancellationReason(request.getReason());
        order.setCancelledAt(LocalDateTime.now());

        // Save cancellation in status history
        OrderStatusHistory history = OrderStatusHistory.builder()
                .order(order)
                .status(OrderStatus.CANCELLED)
                .changedAt(LocalDateTime.now())

                .build();
        orderStatusHistoryRepository.save(history);

        return mapToDto(orderRepository.save(order));
    }


    @Override
    public List<OrderDto> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return orders.stream().map(this::mapToDto).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }

        // Delete all related order status history entries first
        orderStatusHistoryRepository.deleteByOrderId(id);

        // Now delete the order
        orderRepository.deleteById(id);
    }


}
