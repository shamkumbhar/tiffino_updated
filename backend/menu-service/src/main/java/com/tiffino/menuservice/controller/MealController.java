package com.tiffino.menuservice.controller;

import com.tiffino.menuservice.dto.MealDTO;
import com.tiffino.menuservice.dto.MealWithIngredientsDTO;
import com.tiffino.menuservice.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
//@CrossOrigin("http://13.200.6.63:4200")
public class MealController {

    private final MealService mealService;

    @PostMapping("/createMeal")
    public ResponseEntity<MealDTO> create(@RequestBody MealDTO dto) {
        return ResponseEntity.ok(mealService.createMeal(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<MealDTO>> getAll() {
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    @GetMapping("/date")
    public ResponseEntity<List<MealDTO>> getByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(mealService.getMealsByDate(date));
    }

    @GetMapping("/with-ingredients")
    public ResponseEntity<List<MealWithIngredientsDTO>> getMealsWithIngredients(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(mealService.getMealsWithIngredients(date));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealDTO> update(@PathVariable Long id, @RequestBody MealDTO dto) {
        return ResponseEntity.ok(mealService.updateMeal(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
