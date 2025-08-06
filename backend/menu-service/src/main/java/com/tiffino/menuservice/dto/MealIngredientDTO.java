package com.tiffino.menuservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealIngredientDTO {
    private Long id;
    private Long mealId;
    private Long ingredientId;
    private double quantity;
}
