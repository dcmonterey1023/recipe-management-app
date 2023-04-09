package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.constants.UnitOfMeasure;
import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.entity.Nutrition;
import com.recipe.recipemanagementapp.exception.*;
import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.repository.IngredientRepository;
import com.recipe.recipemanagementapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final IngredientService ingredientService;
    private final NutritionService nutritionService;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             CategoryService categoryService,
                             IngredientService ingredientService,
                             NutritionService nutritionService){
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.ingredientService = ingredientService;
        this.nutritionService = nutritionService;
    }

    @Override
    public RecipeResponse getAllRecipe() {
        RecipeResponse recipeResponse = new RecipeResponse();
        log.info("RecipeService getAllRecipe: calling recipe repository");
        recipeResponse.setRecipes(recipeRepository.findAll());
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
        validateAddRecipe(recipe);
        recipeRepository.save(transformRecipe(recipe));
        log.info("RecipeService createRecipe: done saving new recipe {}", recipe.getName());
    }

    @Override
    public void createRecipes(List<Recipe> recipes){
        recipes.forEach(recipe -> {
            validateAddRecipe(recipe);
            transformRecipe(recipe);
        });
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
        validateRecipeCategory(recipe.getCategory());
        recipeRepository.save(transformRecipe(recipe));
    }

    private Recipe transformRecipe(Recipe recipe){
        recipe.setNutritions(mapNutritionValues(recipe));
        recipe.setIngredients(mapIngredients(recipe));
        return recipe;
    }

    private Set<Nutrition> mapNutritionValues(Recipe recipe){
        return nutritionService.mapRecipeToNutrient(recipe);
    }

    private void validateAddRecipe(Recipe recipe){
        Optional<Recipe> recipeOptional = recipeRepository.findByName(recipe.getName());
        if(recipeOptional.isPresent()){
            throw new RecipeAlreadyExistException(
                    String.format("Recipe %s already exist.", recipe.getName()));
        }
        validateRecipeCategory(recipe.getCategory());
        validateTimeUnits(recipe.getCook_time_unit(), recipe.getPrep_time_unit());
    }

    private void validateTimeUnits(String... unitOfTime){
        List<String> chronoUnits = Arrays.stream(ChronoUnit.values()).map(Enum::name).toList();

        boolean invalidUnitOfTime = Arrays.stream(unitOfTime)
                .anyMatch(unit -> Strings.isBlank(unit) || !chronoUnits.contains(unit.toUpperCase()));

        if(invalidUnitOfTime) throw new InvalidRecipeException("Preparation or Cooking time is not accepted.");
    }

    private void validateRecipeCategory(String categoryName) {
        categoryService.validateCategory(categoryName);
    }

    private Set<Ingredient> mapIngredients(Recipe recipe){
        return ingredientService.mapRecipeToIngredient(recipe);
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
