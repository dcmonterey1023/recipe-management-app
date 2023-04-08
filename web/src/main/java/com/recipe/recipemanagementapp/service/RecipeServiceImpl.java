package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.constants.UnitOfMeasure;
import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.exception.RecipeAlreadyExistException;
import com.recipe.recipemanagementapp.exception.RecipeNotFoundException;
import com.recipe.recipemanagementapp.exception.RecipeSearchException;
import com.recipe.recipemanagementapp.exception.UnitOfMeasureNotValidException;
import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.repository.IngredientRepository;
import com.recipe.recipemanagementapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final IngredientRepository ingredientRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             CategoryService categoryService,
                             IngredientRepository ingredientRepository){
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public RecipeResponse getAllRecipe() {
        RecipeResponse recipeResponse = new RecipeResponse();
        log.info("RecipeService getAllRecipe: calling recipe repository");
        recipeRepository.findAll()
                .forEach(recipe -> recipeResponse.getRecipes().add(recipe));
        recipeResponse.setCount(recipeResponse.getRecipes().size());
        log.info("RecipeService getAllRecipe: done calling recipe repository");
        return recipeResponse;
    }

    @Override
    public Recipe getRecipeById(long id) {
        log.info("RecipeService getRecipeById: calling recipe repository using id {}", id);
        return recipeRepository
                .findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe Not Found"));
    }

    @Override
    public RecipeResponse searchRecipe(RecipeSearchRequest recipeSearchRequest) {

        RecipeResponse recipeResponse = new RecipeResponse();
        int serving = convertStringToInt(recipeSearchRequest.getServing());

        recipeRepository.findAllRecipeWithFilter(recipeSearchRequest.getCategory(),
                        recipeSearchRequest.getInstruction(),
                        recipeSearchRequest.getIngredientInclude(),
                        recipeSearchRequest.getIngredientExclude(),
                        serving)
                .forEach(recipe -> recipeResponse.getRecipes().add(recipe));

        recipeResponse.setCount(recipeResponse.getRecipes().size());
        return recipeResponse;
    }

    @Override
    public void createRecipe(Recipe recipe) {
        log.info("RecipeService createRecipe: check if recipe name {} is already existing", recipe.getName());
        recipeRepository.save(transformRecipe(recipe));
        log.info("RecipeService createRecipe: done saving new recipe {}", recipe.getName());
    }

    @Override
    public void createRecipes(List<Recipe> recipes){
        recipes.forEach(this::validateAddRecipe);
        recipeRepository.saveAll(recipes);
    }

    @Override
    public void deleteRecipeById(long id) {
        log.info("RecipeService deleteRecipeById: check if recipe with id {} exist.", id);
        recipeRepository.findById(id).orElseThrow(
                () -> new RecipeNotFoundException(
                        String.format("Can't delete recipe with id %d. Id not found.", id)
                )
        );

        log.info("RecipeService deleteRecipeById: done deleting recipe with id {}.", id);
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void updateRecipeById(Recipe recipe, long id) {

        getRecipeById(id);
        Recipe mappedRecipe = transformRecipe(recipe);
        ingredientRepository.deleteAllByRecipeId(id);
        recipeRepository.save(mappedRecipe);
    }

    private Recipe transformRecipe(Recipe recipe){
        validateAddRecipe(recipe);
        return mapIngredients(recipe);
    }

    private void validateAddRecipe(Recipe recipe){
        Optional<Recipe> recipeOptional = recipeRepository.findByName(recipe.getName());
        if(recipeOptional.isPresent()){
            throw new RecipeAlreadyExistException(
                    String.format("Recipe %s already exist.", recipe.getName()));
        }
        validateRecipeCategory(recipe.getCategory());
    }

    private void validateRecipeCategory(String categoryName){
        categoryService.validateCategory(categoryName);
    }

    private Recipe mapIngredients(Recipe recipe){

        Set<Ingredient> ingredients = recipe.getIngredients()
                .stream()
                .map(ingredient -> Ingredient
                        .builder()
                        .name(ingredient.getName())
                        .description(ingredient.getDescription())
                        .count(ingredient.getCount())
                        .unitOfMeasure(validateUnitOfMeasure(ingredient.getUnitOfMeasure()).name())
                        .recipe(recipe)
                        .build())
                .collect(Collectors.toSet());

        recipe.setIngredients(ingredients);
        return recipe;
    }

    private UnitOfMeasure validateUnitOfMeasure(String unitOfMeasure){

        return Arrays.stream(UnitOfMeasure.values())
                .filter( unit -> unit.name().equalsIgnoreCase(unitOfMeasure))
                .findAny()
                .orElseThrow(
                        () -> new UnitOfMeasureNotValidException(
                                String.format("Unit of Measure %s not valid.", unitOfMeasure)
                        )
                );
    }

    private int convertStringToInt(String str){
        log.info("Validating search filter serving [{}]", str);
        if(testIfValidInt().test(str)){
            log.info("Validating search filter serving [{}] failed", str);
            throw new RecipeSearchException(String.format("filter serving is passed but has invalid value of %s", str));
        }
        return Strings.isBlank(str) ? 0 : Integer.parseInt(str);
    }

    private Predicate<String> testIfValidInt(){
        return stringValue ->
                Strings.isNotBlank(stringValue) && !stringValue.matches("-?(0|[1-9]\\d*)");
    }
}
