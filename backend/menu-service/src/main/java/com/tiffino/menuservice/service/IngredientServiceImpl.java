package com.tiffino.menuservice.service;

import com.tiffino.menuservice.dto.IngredientDTO;
import com.tiffino.menuservice.entity.Ingredient;
import com.tiffino.menuservice.exception.ResourceNotFoundException;
import com.tiffino.menuservice.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public IngredientDTO createIngredient(IngredientDTO dto) {
        Ingredient ingredient = new Ingredient();
        BeanUtils.copyProperties(dto, ingredient);
        return toDTO(ingredientRepository.save(ingredient));
    }

    @Override
    public IngredientDTO getIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ID: " + id));
        return toDTO(ingredient);
    }

    @Override
    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public IngredientDTO updateIngredient(Long id, IngredientDTO dto) {
        Ingredient existing = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ID: " + id));
        existing.setName(dto.getName());
        existing.setUnit(dto.getUnit());
        existing.setCalories(dto.getCalories());
        return toDTO(ingredientRepository.save(existing));
    }

    @Override
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

    private IngredientDTO toDTO(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        BeanUtils.copyProperties(ingredient, dto);
        return dto;
    }
}
