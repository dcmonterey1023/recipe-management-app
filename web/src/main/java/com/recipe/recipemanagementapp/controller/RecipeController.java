package com.recipe.recipemanagementapp.controller;

import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.service.RecipeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.recipe.recipemanagementapp.constants.MessageConstants.*;

@RestController
@RequestMapping("/recipe")
@Slf4j
@Validated
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeResponse> getAllRecipes(){
        log.info(GET_ALL_RECIPE + "Start");
        return new ResponseEntity<>(recipeService.getAllRecipes(), HttpStatus.OK);
    }

    @GetMapping(value = "/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> getRecipeById(@PathVariable @Min(1) long recipeId){
        log.info(GET_RECIPE_BY_ID + "Start", recipeId);
        return new ResponseEntity<>(recipeService.getRecipeById(recipeId), HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeResponse> searchRecipes(RecipeSearchRequest recipeSearchRequest) {
        log.info(SEARCH_RECIPE + "Start");
        return new ResponseEntity<>(recipeService.searchRecipe(recipeSearchRequest), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRecipe(@RequestBody @Valid Recipe recipe){
        log.info(CREATE_RECIPE + "Start");
        recipeService.createRecipe(recipe);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }

    @PostMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRecipes(@RequestBody @Valid List<Recipe> recipes){
        log.info(CREATE_MULTIPLE_RECIPE + "Start");
        recipeService.createRecipes(recipes);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable long recipeId){
        log.info(DELETE_RECIPE + "Start", recipeId);
        recipeService.deleteRecipeById(recipeId);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

    @PatchMapping(value = "/{recipeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateRecipe(@RequestBody @Valid Recipe recipe, @PathVariable long recipeId){
        log.info(UPDATE_RECIPE + "Start", recipeId);
        recipeService.updateRecipeById(recipe, recipeId);
        return new ResponseEntity<>("Successfully Updated", HttpStatus.OK);
    }

}
