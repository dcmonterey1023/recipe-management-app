package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;

public interface RecipeService {
    public RecipeDto getRecipeById(long id);
    public RecipeResponse searchRecipes(RecipeSearchRequest recipeSearchRequest);
    public RecipeDto createRecipe(RecipeDto recipe);
    public void deleteRecipeById(long id);
    public RecipeDto updateRecipeById(RecipeDto recipe, long id);

}
