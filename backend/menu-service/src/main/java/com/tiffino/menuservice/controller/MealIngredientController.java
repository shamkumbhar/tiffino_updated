package com.tiffino.menuservice.controller;

import com.tiffino.menuservice.dto.MealIngredientDTO;
import com.tiffino.menuservice.service.MealIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal-ingredients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
// @CrossOrigin("http://13.200.6.63:4200")
public class MealIngredientController {

    private final MealIngredientService mealIngredientService;

    @PostMapping("/addIngredientToMeal")
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

