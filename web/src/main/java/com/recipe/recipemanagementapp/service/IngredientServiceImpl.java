package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.IngredientDto;
import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.exception.IngredientAlreadyExistException;
import com.recipe.recipemanagementapp.mapper.IngredientMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientMapper mapper;

    public IngredientServiceImpl(IngredientMapper mapper){
        this.mapper = mapper;
    }
    @Override
    public Set<Ingredient> mapRecipeToIngredient(RecipeDto recipeDto, Recipe recipe) {

        validateIngredients(recipeDto.getIngredients());

        return recipeDto.getIngredients()
                .stream()
                .map(ingredient -> {
                    Ingredient mappedIngredient = mapper.mapIngredientDtoToIngredient(ingredient);
                    mappedIngredient.setRecipe(recipe);
                    return mappedIngredient;
                })
                .collect(Collectors.toSet());
    }

    private void validateIngredients(Set<IngredientDto> ingredients){
        long count = ingredients
                .stream()
                .map(IngredientDto::getName)
                .distinct()
                .count();

        if(count != ingredients.size()){
            throw new IngredientAlreadyExistException("Duplicate ingredients found.");
        }
    }
}
