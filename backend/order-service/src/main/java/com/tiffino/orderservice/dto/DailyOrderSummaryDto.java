package com.tiffino.orderservice.dto;

import com.tiffino.orderservice.enumss.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyOrderSummaryDto {

    private LocalDate date;
    private UserDto user;
    private List<MealType> mealTypes;
}
