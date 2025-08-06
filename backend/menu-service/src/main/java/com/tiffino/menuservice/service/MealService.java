package com.tiffino.menuservice.service;

import com.tiffino.menuservice.dto.MealDTO;
import com.tiffino.menuservice.dto.MealWithIngredientsDTO;

import java.time.LocalDate;
import java.util.List;

public interface MealService {
    MealDTO createMeal(MealDTO dto);
    MealDTO getMealById(Long id);
    List<MealDTO> getAllMeals();
    MealDTO updateMeal(Long id, MealDTO dto);
    void deleteMeal(Long id);

    List<MealDTO> getMealsByDate(LocalDate date);
    List<MealWithIngredientsDTO> getMealsWithIngredients(LocalDate date);
}
