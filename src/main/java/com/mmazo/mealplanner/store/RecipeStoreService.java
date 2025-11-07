package com.mmazo.mealplanner.store;

import com.mmazo.mealplanner.recipe.dto.RecipeDTO;
import java.util.Collections;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeStoreService {

    private static final String RECIPE_ID_KEY = "recipeId";

    private final VectorStore vectorStore;

    @Autowired
    public RecipeStoreService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /**
     * Creates or updates recipe embedding in the vector store.
     * @param recipe recipe
     */
    public void saveRecipeInStore(RecipeDTO recipe) {
        String text = """
                      %s.
                      Ingredients: %s
                      Tags: %s
                      """.formatted(recipe.getName(), recipe.getIngredients(), recipe.getTags());

        Map<String, Object> metadata = new HashMap<>();
        metadata.put(RECIPE_ID_KEY, recipe.getRecipeId());

        Document doc = new Document(
                String.valueOf(recipe.getRecipeId()),
                text,
                metadata
        );

        vectorStore.add(List.of(doc));
    }

    /**
     * Removes recipe embedding from vector store
     * @param recipeId recipe id
     */
    public void removeRecipeFromStore(Long recipeId) {
        vectorStore.delete(List.of(String.valueOf(recipeId)));
    }

    /**
     * Performs search in recipes vector store
     * @param searchQuery search query like "Find all vegetarian recipes"
     * @return a list of relevant recipe ids
     */
    public List<Long> searchRecipesInStore(String searchQuery) {
        List<Document> relevantDocs = vectorStore.similaritySearch(searchQuery);

        if (relevantDocs != null) {
            return relevantDocs.stream()
                    .map(doc -> Long.valueOf(doc.getMetadata().get(RECIPE_ID_KEY).toString()))
                    .toList();
        }

        return Collections.emptyList();
    }
}

