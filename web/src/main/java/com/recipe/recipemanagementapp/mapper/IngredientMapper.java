package com.recipe.recipemanagementapp.mapper;

import com.recipe.recipemanagementapp.dto.IngredientDto;
import com.recipe.recipemanagementapp.entity.Ingredient;
import org.mapstruct.Mapper;

@Mapper
public interface IngredientMapper {
    Ingredient mapIngredientDtoToIngredient(IngredientDto dto);
}
