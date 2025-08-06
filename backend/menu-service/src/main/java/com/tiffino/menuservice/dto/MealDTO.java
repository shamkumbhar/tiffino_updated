package com.tiffino.menuservice.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealDTO {
    private Long id;
    private String mealName;
    private String mealType;
    private LocalDate date;
    private Long cuisineId;
}
