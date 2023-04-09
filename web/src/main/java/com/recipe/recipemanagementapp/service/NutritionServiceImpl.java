package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.entity.Nutrition;
import com.recipe.recipemanagementapp.entity.Recipe;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NutritionServiceImpl implements NutritionService {

    @Override
    public Set<Nutrition> mapRecipeToNutrient(Recipe recipe) {

        return recipe.getNutritions()
                .stream()
                .map(nutrition -> {
                    nutrition.setRecipe(recipe);
                    return nutrition;
                }).collect(Collectors.toSet());
    }
}
