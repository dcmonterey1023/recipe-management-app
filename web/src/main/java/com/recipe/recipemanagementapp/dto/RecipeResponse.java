package com.recipe.recipemanagementapp.dto;

import com.recipe.recipemanagementapp.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponse {

    public int count;
    public List<Recipe> recipes = new ArrayList<>();
}
