package com.tiffino.menuservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealWithIngredientsDTO {
    private Long id;
    private String mealName;
    private String mealType;
    private LocalDate date;
    private Long cuisineId;
    private List<MealIngredientDetailDTO> ingredients;
}
