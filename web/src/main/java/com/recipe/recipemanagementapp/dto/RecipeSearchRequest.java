package com.recipe.recipemanagementapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSearchRequest {

    private String category;
    private String instruction;
    private String serving;
    private String ingredientInclude;
    private String ingredientExclude;

}
