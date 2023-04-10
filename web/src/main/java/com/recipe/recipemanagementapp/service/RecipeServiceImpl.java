package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.constants.UnitOfMeasure;
import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.entity.Instruction;
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

import static com.recipe.recipemanagementapp.constants.ErrorMessageConstants.*;
import static com.recipe.recipemanagementapp.constants.MessageConstants.*;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final IngredientService ingredientService;
    private final NutritionService nutritionService;
    private final InstructionService instructionService;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             CategoryService categoryService,
                             IngredientService ingredientService,
                             NutritionService nutritionService,
                             InstructionService instructionService){
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.ingredientService = ingredientService;
        this.nutritionService = nutritionService;
        this.instructionService = instructionService;
    }

    @Override
    public RecipeResponse getAllRecipes() {
        log.info(GET_ALL_RECIPE + "retrieving recipe from repository.");
        List<Recipe> recipes = recipeRepository.findAll();
        log.info(GET_ALL_RECIPE + "done retrieving recipes with {} records.", recipes.size());
        return new RecipeResponse(recipes, recipes.size());
    }

    @Override
    public Recipe getRecipeById(long id) {
        log.info(GET_RECIPE_BY_ID + "retrieving recipe from repository", id);
        return recipeRepository
                .findById(id)
                .orElseThrow(
                        () -> new RecipeNotFoundException(String.format(RECIPE_NOT_FOUND, id))
                );
    }

    @Override
    public RecipeResponse searchRecipe(RecipeSearchRequest recipeSearchRequest) {
        int serving = convertStringToInt(recipeSearchRequest.serving());

        log.info(SEARCH_RECIPE + "retrieving recipes using passed filters.");
        List<Recipe> recipes = recipeRepository.findAllRecipeWithFilter(recipeSearchRequest.category(),
                recipeSearchRequest.instruction(),
                recipeSearchRequest.ingredientInclude(),
                recipeSearchRequest.ingredientExclude(),
                serving);
        return new RecipeResponse(recipes, recipes.size());
    }

    @Override
    public void createRecipe(Recipe recipe) {
        validateAddRecipe(recipe);
        transformRecipe(recipe);
        log.info(CREATE_RECIPE + "saving new recipe {}", recipe.getName());
        recipeRepository.save(recipe);
        log.info(CREATE_RECIPE + "done saving new recipe {}", recipe.getName());
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
        //TODO use Predicate negate
        log.info(DELETE_RECIPE + "check if recipe exist", id);
        recipeRepository.findById(id).orElseThrow(
                () -> new RecipeNotFoundException(
                        String.format(RECIPE_NOT_FOUND, id)
                )
        );
        recipeRepository.deleteById(id);
        log.info(DELETE_RECIPE + "done deleting recipe.", id);
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
        recipe.setInstructions(mapInstructions(recipe));
        recipe.setIngredients(mapIngredients(recipe));
        return recipe;
    }

    private void validateAddRecipe(Recipe recipe){
        log.info(VALIDATE_RECIPE_IF_EXIST, recipe.getName());
        //TODO Use Predicate negate
        Optional<Recipe> recipeOptional = recipeRepository.findByName(recipe.getName());
        if(recipeOptional.isPresent()){
            throw new RecipeAlreadyExistException(
                    String.format(RECIPE_ALREADY_EXIST, recipe.getName()));
        }
        validateRecipeCategory(recipe.getCategory());
        validateTimeUnits(recipe.getCook_time_unit(), recipe.getPrep_time_unit());
    }

    private void validateTimeUnits(String... unitOfTime){
        List<String> chronoUnits = Arrays.stream(ChronoUnit.values()).map(Enum::name).toList();
        boolean invalidUnitOfTime = Arrays.stream(unitOfTime)
                .anyMatch(unit -> Strings.isBlank(unit) || !chronoUnits.contains(unit.toUpperCase()));

        if(invalidUnitOfTime) throw new InvalidRecipeException(INVALID_PREP_COOK_TIME);
    }

    private void validateRecipeCategory(String categoryName) {
        categoryService.validateCategory(categoryName);
    }

    private Set<Ingredient> mapIngredients(Recipe recipe){
        log.info(RECIPE_VALUES_MAPPING, "ingredients", recipe.getName());
        return ingredientService.mapRecipeToIngredient(recipe);
    }
    private Set<Nutrition> mapNutritionValues(Recipe recipe){
        log.info(RECIPE_VALUES_MAPPING, "nutrition facts", recipe.getName());
        return nutritionService.mapRecipeToNutrient(recipe);
    }
    private Set<Instruction> mapInstructions(Recipe recipe){
        log.info(RECIPE_VALUES_MAPPING, "instructions", recipe.getName());
        return instructionService.mapInstructionToRecipe(recipe);
    }

    private int convertStringToInt(String str){
        if(testIfValidInt().test(str)){
            throw new RecipeSearchException(String.format(INVALID_SEARCH_FILTER_SERVING, str));
        }
        return Strings.isBlank(str) ? 0 : Integer.parseInt(str);
    }

    private Predicate<String> testIfValidInt(){
        return stringValue ->
                Strings.isNotBlank(stringValue) && !stringValue.matches("-?(0|[1-9]\\d*)");
    }
}
