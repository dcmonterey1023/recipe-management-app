package com.recipe.recipemanagementapp.dto;

import com.recipe.recipemanagementapp.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
public record RecipeResponse(List<RecipeDto> recipes, int count) {
    public RecipeResponse(){
        this(new ArrayList<>(), 0);
    }
}
