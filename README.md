# AI Meal Planner :)

A small demo Spring Boot + Spring AI + Swagger UI app that generates a weekly meals plan and shopping list using OpenAI API and RAG technique.

## RAG (Retrieval-Augmented Generation) technique applied

### Retrieval step

The vector store plays a role of search engine and delivers relevant recipes based on the search query like "Gimme vegetarian plan".

### Augmentation step

Only these relevant recipes are added to the prompt. 

### Generation step

The OpenAI LLM (accessed over API) generates the plan and shopping least based on given prompt.

## Technology behind

- Recipes are stored in the H2 database.
- SimpleVectorStore (Open AI Vector Store) is used to store the recipe embeddings and perform search for relevant recipes.
- OpenAI Chat Client is used to generate weekly meals plan and shopping list based on relevant recipes.