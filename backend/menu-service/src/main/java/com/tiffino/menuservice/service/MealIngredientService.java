package com.tiffino.menuservice.service;

import com.tiffino.menuservice.dto.MealIngredientDTO;

import java.util.List;

public interface MealIngredientService {
    MealIngredientDTO addIngredientToMeal(MealIngredientDTO dto);
    MealIngredientDTO getMealIngredientById(Long id);
    List<MealIngredientDTO> getAllMealIngredients();
    List<MealIngredientDTO> getIngredientsByMealId(Long mealId);
    MealIngredientDTO updateMealIngredient(Long id, MealIngredientDTO dto);
    void deleteMealIngredient(Long id);
}
