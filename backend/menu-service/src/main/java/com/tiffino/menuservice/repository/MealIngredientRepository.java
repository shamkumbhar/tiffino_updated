package com.tiffino.menuservice.repository;

import com.tiffino.menuservice.entity.MealIngredient;
import com.tiffino.menuservice.entity.Meal;
import com.tiffino.menuservice.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MealIngredientRepository extends JpaRepository<MealIngredient, Long> {
    List<MealIngredient> findByMeal(Meal meal);
    List<MealIngredient> findByIngredient(Ingredient ingredient);
}
