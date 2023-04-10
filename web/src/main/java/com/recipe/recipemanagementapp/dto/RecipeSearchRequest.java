package com.recipe.recipemanagementapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Builder
public record RecipeSearchRequest(String category, String instruction, String serving,
                                  String ingredientInclude, String ingredientExclude) {
}
