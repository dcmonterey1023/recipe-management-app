package com.recipe.recipemanagementapp.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Builder
@Data
public class RecipeSearchRequest {

    private String category;
    private String instruction;
    private String serving;
    private String ingredientInclude;
    private String ingredientExclude;

}
