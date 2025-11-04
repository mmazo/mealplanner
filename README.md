# AI Meal Planner :)

A small demo Spring Boot + Spring AI + Swagger UI app that generates a weekly meals plan and shopping list using OpenAI API and RAG technique.

- Recipes are stored in the file-based H2 database.
- OpenAI Vector Store is used to store the recipe embeddings.
- OpenAI Chat Client is used to generate weekly meals plan and shopping list based on recipes stored in the vector store.