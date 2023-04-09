package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.exception.IngredientAlreadyExistException;
import com.recipe.recipemanagementapp.repository.IngredientRepository;
import com.recipe.recipemanagementapp.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 UnitOfMeasureService unitOfMeasureService){
        this.ingredientRepository = ingredientRepository;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public void addRecipeIngredient(Ingredient ingredient, long id) {
//        Recipe recipe = recipeService.getRecipeById(id);
//
//        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
//                .stream()
//                .filter(e -> e.getName().equalsIgnoreCase(ingredient.getName()))
//                .findAny();
//
//        if(ingredientOptional.isPresent()){
//            throw new IngredientAlreadyExistException(
//                    String.format("Recipe with id %d has already a %s ingredient.", id, ingredient.getName()));
//        }
//        ingredient.setRecipe(recipe);
//        recipe.getIngredients().add(ingredient);
    }

    @Override
    public List<Ingredient> getAllIngredientsByRecipe(long recipeId) {
        return ingredientRepository.findAllByRecipeId(recipeId);
    }

    @Override
    public Set<Ingredient> mapRecipeToIngredient(Recipe recipe) {

        validateIngredients(recipe.getIngredients());
        return recipe.getIngredients()
                .stream()
                .map(ingredient -> {
                    validateIngredientUnitOfMeasure(ingredient.getUnitOfMeasure());
                    ingredient.setRecipe(recipe);
                    return ingredient;
                })
                .collect(Collectors.toSet());
    }

    private void validateIngredients(Set<Ingredient> ingredients){
        long count = ingredients
                .stream()
                .map(Ingredient::getName)
                .distinct()
                .count();

        if(count != ingredients.size()){
            throw new IngredientAlreadyExistException("Duplicate ingredients found.");
        }
    }

    private void validateIngredientUnitOfMeasure(String unitOfMeasure){
        unitOfMeasureService.validateUnitOfMeasure(unitOfMeasure);
    }
}
