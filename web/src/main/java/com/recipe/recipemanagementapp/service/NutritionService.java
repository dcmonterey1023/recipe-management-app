package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.entity.Nutrition;
import com.recipe.recipemanagementapp.entity.Recipe;

import java.util.Set;

public interface NutritionService {
    public Set<Nutrition> mapRecipeToNutrient(Recipe recipe);
}
