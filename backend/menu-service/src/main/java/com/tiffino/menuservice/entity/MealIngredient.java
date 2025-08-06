package com.tiffino.menuservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meal_ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(nullable = false)
    private double quantity; // e.g., 100 grams
}
