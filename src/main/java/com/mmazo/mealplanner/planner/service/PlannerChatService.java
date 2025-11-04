package com.mmazo.mealplanner.planner.service;

import com.mmazo.mealplanner.recipe.dto.RecipeDTO;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class PlannerChatService {

    private final OpenAiChatModel chatModel;

    @Autowired
    public PlannerChatService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public Map<String, Object> generatePlan(@RequestBody List<RecipeDTO> recipes) {
        Prompt prompt = this.getPrompt(recipes);
        String content = chatModel.call(prompt).getResult().getOutput().getContent();

        Map<String, Object> result;
        try {
            result = Map.of("result", new com.fasterxml.jackson.databind.ObjectMapper().readValue(content, Map.class));
        } catch (Exception e) {
            result = Map.of("raw", content);
        }

        return result;
    }

    private Prompt getPrompt(List<RecipeDTO> recipes) {
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
