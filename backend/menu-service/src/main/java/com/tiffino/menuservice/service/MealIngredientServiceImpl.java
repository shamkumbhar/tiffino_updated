package com.tiffino.menuservice.service;

import com.tiffino.menuservice.dto.MealIngredientDTO;
import com.tiffino.menuservice.entity.Ingredient;
import com.tiffino.menuservice.entity.Meal;
import com.tiffino.menuservice.entity.MealIngredient;
import com.tiffino.menuservice.exception.ResourceNotFoundException;
import com.tiffino.menuservice.repository.IngredientRepository;
import com.tiffino.menuservice.repository.MealIngredientRepository;
import com.tiffino.menuservice.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealIngredientServiceImpl implements MealIngredientService {

    private final MealIngredientRepository mealIngredientRepository;
    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public MealIngredientDTO addIngredientToMeal(MealIngredientDTO dto) {
        Meal meal = mealRepository.findById(dto.getMealId())
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + dto.getMealId()));

        Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ID: " + dto.getIngredientId()));

        MealIngredient entity = new MealIngredient();
        entity.setMeal(meal);
        entity.setIngredient(ingredient);
        entity.setQuantity(dto.getQuantity());

        return toDTO(mealIngredientRepository.save(entity));
    }

    @Override
    public MealIngredientDTO getMealIngredientById(Long id) {
        MealIngredient entity = mealIngredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MealIngredient not found with ID: " + id));
        return toDTO(entity);
    }

    @Override
    public List<MealIngredientDTO> getIngredientsByMealId(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + mealId));

        List<MealIngredient> entities = mealIngredientRepository.findByMeal(meal);
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MealIngredientDTO> getAllMealIngredients() {
        return mealIngredientRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MealIngredientDTO updateMealIngredient(Long id, MealIngredientDTO dto) {
        MealIngredient existing = mealIngredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MealIngredient not found with ID: " + id));

        Meal meal = mealRepository.findById(dto.getMealId())
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + dto.getMealId()));

        Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ID: " + dto.getIngredientId()));

        existing.setMeal(meal);
        existing.setIngredient(ingredient);
        existing.setQuantity(dto.getQuantity());

        return toDTO(mealIngredientRepository.save(existing));
    }

    @Override
    public void deleteMealIngredient(Long id) {
        if (!mealIngredientRepository.existsById(id)) {
            throw new ResourceNotFoundException("MealIngredient not found with ID: " + id);
        }
        mealIngredientRepository.deleteById(id);
    }

    private MealIngredientDTO toDTO(MealIngredient entity) {
        return new MealIngredientDTO(
                entity.getId(),
                entity.getMeal().getId(),
                entity.getIngredient().getId(),
                entity.getQuantity()
        );
    }
}
