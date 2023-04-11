package com.recipe.recipemanagementapp.dto;

import com.recipe.recipemanagementapp.constants.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Builder
public record RecipeSearchRequest(Categories category, String instruction, Integer serving,
                                  String ingredientInclude, String ingredientExclude) {
}
