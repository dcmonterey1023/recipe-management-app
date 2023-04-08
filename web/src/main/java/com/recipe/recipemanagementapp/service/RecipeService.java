package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.entity.Recipe;

import java.util.List;

public interface RecipeService {

    public RecipeResponse getAllRecipe();

    public Recipe getRecipeById(long id);

    public RecipeResponse searchRecipe(RecipeSearchRequest recipeSearchRequest);

    public void createRecipe(Recipe recipe);
    public void createRecipes(List<Recipe> recipes);

    public void deleteRecipeById(long id);

    public void updateRecipeById(Recipe recipe, long id);

}
