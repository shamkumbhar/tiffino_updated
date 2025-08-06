package com.tiffino.menuservice.repository;

import com.tiffino.menuservice.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
