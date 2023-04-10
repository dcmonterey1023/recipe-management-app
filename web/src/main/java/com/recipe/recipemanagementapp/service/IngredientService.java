package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Recipe;

import java.util.List;
import java.util.Set;

public interface IngredientService {

    public Set<Ingredient> mapRecipeToIngredient(Recipe recipe);

}
