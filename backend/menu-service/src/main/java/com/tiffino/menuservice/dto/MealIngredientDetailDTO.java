package com.tiffino.menuservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealIngredientDetailDTO {
    private Long ingredientId;
    private String ingredientName;
    private double quantity;
    private String unit;
}
