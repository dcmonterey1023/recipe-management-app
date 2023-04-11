package com.recipe.recipemanagementapp.mapper;

import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.entity.Recipe;
import org.mapstruct.Mapper;

@Mapper
public interface RecipeMapper {
    Recipe mapRecipeDtoToRecipe(RecipeDto dto);
    RecipeDto mapRecipeToRecipeDto(Recipe recipe);
}
