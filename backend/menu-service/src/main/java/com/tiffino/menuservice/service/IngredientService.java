package com.tiffino.menuservice.service;

import com.tiffino.menuservice.dto.IngredientDTO;
import java.util.List;

public interface IngredientService {
    IngredientDTO createIngredient(IngredientDTO ingredientDTO);
    IngredientDTO getIngredientById(Long id);
    List<IngredientDTO> getAllIngredients();
    IngredientDTO updateIngredient(Long id, IngredientDTO ingredientDTO);
    void deleteIngredient(Long id);
}
