package com.mmazo.mealplanner.recipe.controller;

import com.mmazo.mealplanner.recipe.dto.RecipeDTO;
import com.mmazo.mealplanner.recipe.service.RecipeService;
import com.mmazo.mealplanner.store.RecipeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeStoreService recipeStoreService;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeStoreService recipeStoreService) {
        this.recipeService = recipeService;
        this.recipeStoreService = recipeStoreService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(recipeService.getRecipeById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAll() {
        return new ResponseEntity<>(recipeService.getAllRecipes(), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<RecipeDTO>> search(@RequestBody String searchQuery) {
        List<Long> relevantRecipeIDs = this.recipeStoreService.searchRecipesInStore(searchQuery);
        return new ResponseEntity<>(this.recipeService.getAllRecipesByIDs(relevantRecipeIDs), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipe) {
        RecipeDTO createdRecipe = this.recipeService.createRecipe(recipe);
        this.recipeStoreService.saveRecipeInStore(createdRecipe);
        return new ResponseEntity<>(createdRecipe, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipe, @PathVariable Long id) {
        RecipeDTO updatedRecipe = this.recipeService.updateRecipe(recipe, id);
        this.recipeStoreService.saveRecipeInStore(updatedRecipe);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.recipeService.deleteRecipeById(id);
        this.recipeStoreService.removeRecipeFromStore(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}