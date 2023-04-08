package com.recipe.recipemanagementapp.controller;

import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.service.RecipeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeResponse> getAllRecipe(){
        log.info("RecipeController: getAllRecipe: Start");
        return new ResponseEntity<>(recipeService.getAllRecipe(), HttpStatus.OK);
    }

    @GetMapping(value = "/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> getRecipeById(@PathVariable long recipeId){
        log.info("RecipeController: getRecipeById: Start");
        return new ResponseEntity<>(recipeService.getRecipeById(recipeId), HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeResponse> searchRecipe(RecipeSearchRequest recipeSearchRequest) {
        log.info("RecipeController: searchRecipe: Start");
        return new ResponseEntity<>(recipeService.searchRecipe(recipeSearchRequest), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createRecipe(@RequestBody @Valid Recipe recipe){
        log.info("RecipeController: createRecipe: Start");
        recipeService.createRecipe(recipe);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<String> createRecipes(@RequestBody @Valid List<Recipe> recipes){
        log.info("RecipeController: createRecipe: Start");
        recipeService.createRecipes(recipes);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable long recipeId){
        log.info("RecipeController: deleteRecipe: Start");
        recipeService.deleteRecipeById(recipeId);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

    @PatchMapping("/{recipeId}")
    public ResponseEntity<String> updateRecipe(@RequestBody Recipe recipe, @PathVariable long recipeId){
        log.info("RecipeController: updateRecipe: Start");
        recipeService.updateRecipeById(recipe, recipeId);
        return new ResponseEntity<>("Successfully Created", HttpStatus.OK);
    }

}
