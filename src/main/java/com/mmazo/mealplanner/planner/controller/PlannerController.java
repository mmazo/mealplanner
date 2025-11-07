package com.mmazo.mealplanner.planner.controller;

import com.mmazo.mealplanner.planner.service.PlannerChatService;
import com.mmazo.mealplanner.recipe.dto.RecipeDTO;
import com.mmazo.mealplanner.recipe.service.RecipeService;
import com.mmazo.mealplanner.store.RecipeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planner/generate-plan")
public class PlannerController {

    private final PlannerChatService plannerChatService;
    private final RecipeService recipeService;
    private final RecipeStoreService recipeStoreService;

    @Autowired
    public PlannerController(PlannerChatService plannerChatService, RecipeService recipeService, RecipeStoreService recipeStoreService) {
        this.plannerChatService = plannerChatService;
        this.recipeService = recipeService;
        this.recipeStoreService = recipeStoreService;
    }

    @PostMapping("/from-body")
    public ResponseEntity<String> generatePlanFromBody(@RequestBody List<RecipeDTO> recipes) {
        return new ResponseEntity<>(this.plannerChatService.generatePlan(recipes), HttpStatus.OK);
    }

    @PostMapping("/from-db")
    public ResponseEntity<String> generatePlanFromDB() {
        List<RecipeDTO> allRecipes = this.recipeService.getAllRecipes();
        return new ResponseEntity<>(this.plannerChatService.generatePlan(allRecipes), HttpStatus.OK);
    }

    @PostMapping("/from-store")
    public ResponseEntity<String> generatePlanFromStore(@RequestBody String searchQuery) {
        List<Long> relevantRecipeIDs = this.recipeStoreService.searchRecipesInStore(searchQuery);
        List<RecipeDTO> relevantRecipes = this.recipeService.getAllRecipesByIDs(relevantRecipeIDs);
        return new ResponseEntity<>(this.plannerChatService.generatePlan(relevantRecipes), HttpStatus.OK);
    }
}
