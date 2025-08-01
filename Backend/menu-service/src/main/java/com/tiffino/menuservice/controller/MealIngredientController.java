package com.tiffino.menuservice.controller;

import com.tiffino.menuservice.dto.MealIngredientDTO;
import com.tiffino.menuservice.service.MealIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meal-ingredients")
@RequiredArgsConstructor
@CrossOrigin("http://13.203.196.191:4200")
public class MealIngredientController {

    private final MealIngredientService mealIngredientService;

    @PostMapping
    public ResponseEntity<MealIngredientDTO> add(@RequestBody MealIngredientDTO dto) {
        return ResponseEntity.ok(mealIngredientService.addIngredientToMeal(dto));
    }

    @GetMapping("/meal/{mealId}")
    public ResponseEntity<List<MealIngredientDTO>> getByMeal(@PathVariable Long mealId) {
        return ResponseEntity.ok(mealIngredientService.getIngredientsByMealId(mealId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mealIngredientService.deleteMealIngredient(id);
        return ResponseEntity.noContent().build();
    }
}

