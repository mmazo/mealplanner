package com.mmazo.mealplanner.recipe.repository;

import com.mmazo.mealplanner.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {}

