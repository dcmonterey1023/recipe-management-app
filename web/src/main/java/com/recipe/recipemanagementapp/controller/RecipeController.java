package com.recipe.recipemanagementapp.controller;

import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.service.RecipeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.recipe.recipemanagementapp.constants.MessageConstants.*;

@RestController
@RequestMapping("/recipes")
@Slf4j
@Validated
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public RecipeResponse searchRecipes(RecipeSearchRequest recipeSearchRequest) {
        log.info(SEARCH_RECIPE + "Start");
        return recipeService.searchRecipes(recipeSearchRequest);
    }
    @GetMapping(value = "/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RecipeDto getRecipeById(@PathVariable @Min(1) long recipeId){
        log.info(GET_RECIPE_BY_ID + "Start", recipeId);
        return recipeService.getRecipeById(recipeId);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public RecipeDto createRecipe(@RequestBody @Valid RecipeDto recipe){
        log.info(CREATE_RECIPE + "Start");
        return recipeService.createRecipe(recipe);
    }
    @DeleteMapping("/{recipeId}")
    public String deleteRecipe(@PathVariable long recipeId){
        log.info(DELETE_RECIPE + "Start", recipeId);
        recipeService.deleteRecipeById(recipeId);
        return "Successfully Deleted";
    }
    @PutMapping(value = "/{recipeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RecipeDto updateRecipe(@RequestBody @Valid RecipeDto recipe, @PathVariable long recipeId){
        log.info(UPDATE_RECIPE + "Start", recipeId);
        return recipeService.updateRecipeById(recipe, recipeId);
    }
}
