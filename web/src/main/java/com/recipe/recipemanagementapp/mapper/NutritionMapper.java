package com.recipe.recipemanagementapp.mapper;

import com.recipe.recipemanagementapp.dto.NutritionDto;
import com.recipe.recipemanagementapp.entity.Nutrition;
import org.mapstruct.Mapper;

@Mapper
public interface NutritionMapper {

    Nutrition mapNutritionDtoToNutrition(NutritionDto dto);
    NutritionDto mapNutritionToNutrition(Nutrition nutrition);

}
