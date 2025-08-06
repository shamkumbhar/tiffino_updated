package com.tiffino.menuservice.service;

import com.tiffino.menuservice.dto.MealDTO;
import com.tiffino.menuservice.dto.MealIngredientDetailDTO;
import com.tiffino.menuservice.dto.MealWithIngredientsDTO;
import com.tiffino.menuservice.entity.*;
import com.tiffino.menuservice.exception.ResourceNotFoundException;
import com.tiffino.menuservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final CuisineRepository cuisineRepository;
    private final MealIngredientRepository mealIngredientRepository;

    @Override
    public MealDTO createMeal(MealDTO dto) {
        Cuisine cuisine = cuisineRepository.findById(dto.getCuisineId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuisine not found with ID: " + dto.getCuisineId()));

        Meal meal = new Meal();
        meal.setMealName(dto.getMealName());
        meal.setMealType(dto.getMealType());
        meal.setDate(dto.getDate());
        meal.setCuisine(cuisine);

        return toDTO(mealRepository.save(meal));
    }

    @Override
    public MealDTO getMealById(Long id) {
        return toDTO(mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + id)));
    }

    @Override
    public List<MealDTO> getAllMeals() {
        return mealRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealDTO> getMealsByDate(LocalDate date) {
        return mealRepository.findByDate(date)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealWithIngredientsDTO> getMealsWithIngredients(LocalDate date) {
        List<Meal> meals = mealRepository.findByDate(date);

        return meals.stream().map(meal -> {
            MealWithIngredientsDTO dto = new MealWithIngredientsDTO();
            dto.setId(meal.getId());
            dto.setMealName(meal.getMealName());
            dto.setMealType(meal.getMealType());
            dto.setDate(meal.getDate());
            dto.setCuisineId(meal.getCuisine() != null ? meal.getCuisine().getId() : null);

            List<MealIngredient> mealIngredients = mealIngredientRepository.findByMeal(meal);
            List<MealIngredientDetailDTO> ingredients = mealIngredients.stream().map(mi -> {
                Ingredient ing = mi.getIngredient();
                return new MealIngredientDetailDTO(
                        ing.getId(),
                        ing.getName(),
                        mi.getQuantity(),
                        ing.getUnit()
                );
            }).collect(Collectors.toList());

            dto.setIngredients(ingredients);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public MealDTO updateMeal(Long id, MealDTO dto) {
        Meal existing = mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + id));

        Cuisine cuisine = cuisineRepository.findById(dto.getCuisineId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuisine not found with ID: " + dto.getCuisineId()));

        existing.setMealName(dto.getMealName());
        existing.setMealType(dto.getMealType());
        existing.setDate(dto.getDate());
        existing.setCuisine(cuisine);

        return toDTO(mealRepository.save(existing));
    }

    @Override
    public void deleteMeal(Long id) {
        if (!mealRepository.existsById(id)) {
            throw new ResourceNotFoundException("Meal not found with ID: " + id);
        }
        mealRepository.deleteById(id);
    }

    private MealDTO toDTO(Meal entity) {
        MealDTO dto = new MealDTO();
        dto.setId(entity.getId());
        dto.setMealName(entity.getMealName());
        dto.setMealType(entity.getMealType());
        dto.setDate(entity.getDate());
        dto.setCuisineId(entity.getCuisine() != null ? entity.getCuisine().getId() : null);
        return dto;
    }
}
