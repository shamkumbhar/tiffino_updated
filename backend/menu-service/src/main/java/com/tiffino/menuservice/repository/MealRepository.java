package com.tiffino.menuservice.repository;

import com.tiffino.menuservice.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByDate(LocalDate date);  // <-- Add this method

}
