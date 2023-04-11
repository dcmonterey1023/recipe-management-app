package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.entity.Nutrition;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.mapper.NutritionMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NutritionServiceImpl implements NutritionService {
    private final NutritionMapper mapper;
    public NutritionServiceImpl(NutritionMapper mapper){
        this.mapper = mapper;
    }
    @Override
    public Set<Nutrition> mapRecipeToNutrient(RecipeDto recipeDto, Recipe recipe) {
        return recipeDto.getNutritions()
                .stream()
                .map(nutrition -> {
                    Nutrition mappedNutrition = mapper.mapNutritionDtoToNutrition(nutrition);
                    mappedNutrition.setRecipe(recipe);
                    return mappedNutrition;
                }).collect(Collectors.toSet());
    }
}
