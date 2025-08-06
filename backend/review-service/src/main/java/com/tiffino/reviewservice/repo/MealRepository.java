package com.tiffino.reviewservice.repo;

import com.tiffino.reviewservice.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MealRepository  extends JpaRepository<Meal , Long> {
    Optional<Meal> findById(Long id);
}
