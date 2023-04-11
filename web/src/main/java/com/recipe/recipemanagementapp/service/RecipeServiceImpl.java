package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Instruction;
import com.recipe.recipemanagementapp.entity.Nutrition;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.exception.RecipeAlreadyExistException;
import com.recipe.recipemanagementapp.exception.RecipeNotFoundException;
import com.recipe.recipemanagementapp.mapper.RecipeMapper;
import com.recipe.recipemanagementapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.recipe.recipemanagementapp.constants.ErrorMessageConstants.RECIPE_ALREADY_EXIST;
import static com.recipe.recipemanagementapp.constants.ErrorMessageConstants.RECIPE_NOT_FOUND;
import static com.recipe.recipemanagementapp.constants.MessageConstants.*;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final NutritionService nutritionService;
    private final InstructionService instructionService;
    private final RecipeMapper mapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             IngredientService ingredientService,
                             NutritionService nutritionService,
                             InstructionService instructionService,
                             RecipeMapper mapper){
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.nutritionService = nutritionService;
        this.instructionService = instructionService;
        this.mapper = mapper;
    }
    @Override
    public RecipeDto getRecipeById(long id) {
        log.info(GET_RECIPE_BY_ID + "retrieving recipe from repository", id);
        Recipe recipe = recipeRepository
                .findById(id)
                .orElseThrow(
                        () -> new RecipeNotFoundException(String.format(RECIPE_NOT_FOUND, id))
                );
        return mapper.mapRecipeToRecipeDto(recipe);
    }

    @Override
    public RecipeResponse searchRecipes(RecipeSearchRequest recipeSearchRequest) {

        log.info(SEARCH_RECIPE + "retrieving recipes using passed filters.");
        List<Recipe> recipes = recipeRepository.findAllRecipeWithFilter(
                recipeSearchRequest.category(),
                recipeSearchRequest.instruction(),
                recipeSearchRequest.ingredientInclude(),
                recipeSearchRequest.ingredientExclude(),
                recipeSearchRequest.serving());
        List<RecipeDto> recipeDtoList = recipes.stream()
                .map(mapper::mapRecipeToRecipeDto)
                .toList();
        return new RecipeResponse(recipeDtoList, recipeDtoList.size());
    }

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        validateAddRecipe(recipeDto);
        Recipe recipeEntity = mapper.mapRecipeDtoToRecipe(recipeDto);
        transformRecipe(recipeDto, recipeEntity);

        log.info(CREATE_RECIPE + "saving new recipe {}", recipeDto.getName());
        Recipe recipe = recipeRepository.save(recipeEntity);
        return mapper.mapRecipeToRecipeDto(recipe);
    }

    @Override
    public void deleteRecipeById(long id) {
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
    public RecipeDto updateRecipeById(RecipeDto recipeDto, long id) {
        recipeRepository.findById(id).orElseThrow(
                        () -> new RecipeNotFoundException(String.format(RECIPE_NOT_FOUND, id)));
        Recipe recipeEntity = mapper.mapRecipeDtoToRecipe(recipeDto);
        recipeEntity.setId(id);
        transformRecipe(recipeDto, recipeEntity);
        Recipe recipeResponse = recipeRepository.save(recipeEntity);
        return mapper.mapRecipeToRecipeDto(recipeResponse);
    }
    private Recipe transformRecipe(RecipeDto recipeDto, Recipe recipe){
        recipe.setIngredients(mapIngredients(recipeDto, recipe));
        recipe.setInstructions(mapInstructions(recipeDto, recipe));
        recipe.setNutritions(mapNutritionValues(recipeDto, recipe));
        return recipe;
    }

    private void validateAddRecipe(RecipeDto recipe){
        log.info(VALIDATE_RECIPE_IF_EXIST, recipe.getName());
        Optional<Recipe> recipeOptional = recipeRepository.findByName(recipe.getName());
        if(recipeOptional.isPresent()){
            throw new RecipeAlreadyExistException(
                    String.format(RECIPE_ALREADY_EXIST, recipe.getName()));
        }
    }

    private Set<Ingredient> mapIngredients(RecipeDto recipeDto, Recipe recipe){
        log.info(RECIPE_VALUES_MAPPING, "ingredients", recipeDto.getName());
        return ingredientService.mapRecipeToIngredient(recipeDto, recipe);
    }
    private Set<Nutrition> mapNutritionValues(RecipeDto recipeDto, Recipe recipe){
        log.info(RECIPE_VALUES_MAPPING, "nutrition facts", recipeDto.getName());
        return nutritionService.mapRecipeToNutrient(recipeDto, recipe);
    }
    private Set<Instruction> mapInstructions(RecipeDto recipeDto, Recipe recipe){
        log.info(RECIPE_VALUES_MAPPING, "instructions", recipeDto.getName());
        return instructionService.mapInstructionToRecipe(recipeDto, recipe);
    }
}
