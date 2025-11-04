package com.mmazo.mealplanner.recipe.mapper;

import com.mmazo.mealplanner.recipe.dto.RecipeDTO;
import com.mmazo.mealplanner.recipe.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RecipeMapper {

    @Mapping(target = "id", ignore = true)
    public abstract Recipe toRecipe(RecipeDTO recipeDTO);

    @Mapping(target = "recipeId", source = "id")
    public abstract RecipeDTO toRecipeDTO(Recipe recipe);
}
