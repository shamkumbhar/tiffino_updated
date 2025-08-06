package com.tiffino.reviewservice.repo;

import com.tiffino.reviewservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order ,Long> {
    Optional<Order> findById(Long id);

}
