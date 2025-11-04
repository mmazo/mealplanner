package com.mmazo.mealplanner.recipe.service;

import com.mmazo.mealplanner.recipe.dto.RecipeDTO;
import com.mmazo.mealplanner.recipe.mapper.RecipeMapper;
import com.mmazo.mealplanner.recipe.model.Recipe;
import com.mmazo.mealplanner.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll().stream().map(this.recipeMapper::toRecipeDTO).toList();
    }

    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe != null) {
            return recipeMapper.toRecipeDTO(recipe);
        }
        return null;
    }

    public RecipeDTO createRecipe(RecipeDTO dto) {
        return this.recipeMapper.toRecipeDTO(recipeRepository.save(this.recipeMapper.toRecipe(dto)));
    }

    public RecipeDTO updateRecipe(RecipeDTO dto, Long id) {
        Recipe recipe = this.recipeMapper.toRecipe(dto);
        recipe.setId(id);
        return this.recipeMapper.toRecipeDTO(recipeRepository.save(recipe));
    }

    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }
}
