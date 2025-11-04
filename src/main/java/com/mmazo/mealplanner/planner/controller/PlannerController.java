package com.mmazo.mealplanner.planner.controller;

import com.mmazo.mealplanner.planner.service.PlannerChatService;
import com.mmazo.mealplanner.recipe.dto.RecipeDTO;
import com.mmazo.mealplanner.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/planner/generate-plan")
public class PlannerController {

    private final PlannerChatService plannerChatService;
    private final RecipeService recipeService;

    @Autowired
    public PlannerController(PlannerChatService plannerChatService, RecipeService recipeService) {
        this.plannerChatService = plannerChatService;
        this.recipeService = recipeService;
    }

    @PostMapping("/from-body-sources")
    public ResponseEntity<Map<String, Object>> generatePlanFromBody(@RequestBody List<RecipeDTO> recipes) {
        return new ResponseEntity<>(this.plannerChatService.generatePlan(recipes), HttpStatus.OK);
    }

    @PostMapping("/from-db-sources")
    public ResponseEntity<Map<String, Object>> generatePlanFromDB() {
        return new ResponseEntity<>(this.plannerChatService.generatePlan(this.recipeService.getAllRecipes()), HttpStatus.OK);
    }
}
