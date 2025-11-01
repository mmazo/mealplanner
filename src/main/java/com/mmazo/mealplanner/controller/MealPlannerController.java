package com.mmazo.mealplanner.controller;

import com.mmazo.mealplanner.model.Recipe;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MealPlannerController {

    private final OpenAiChatModel chatModel;

    @Autowired
    public MealPlannerController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/generate-plan")
    public Map<String, Object> generatePlan(@RequestBody List<Recipe> recipes) {
        Prompt prompt = getPrompt(recipes);

        var response = chatModel.call(prompt);
        String content = response.getResult().getOutput().getContent();

        // Try to parse it as JSON if it's valid, else return raw string
        try {
            return Map.of("result", new com.fasterxml.jackson.databind.ObjectMapper().readValue(content, Map.class));
        } catch (Exception e) {
            return Map.of("raw", content);
        }
    }

    private static Prompt getPrompt(List<Recipe> recipes) {
        String template = """
                You are a meal planner assistant.
                Given this list of recipes in JSON format:
                {recipes}

                Generate:
                1. A weekly meal plan (7 days, 3 meals per day)
                2. A shopping checklist with all needed ingredients (grouped by category if possible)

                Return a JSON object with keys: "mealPlan" and "shoppingList".
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template, Map.of("recipes", recipes));
        return promptTemplate.create();
    }
}
