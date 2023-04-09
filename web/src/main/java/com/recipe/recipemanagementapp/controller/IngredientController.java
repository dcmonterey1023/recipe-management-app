package com.recipe.recipemanagementapp.controller;

import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe/{recipeId}/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<String> addRecipeIngredient(@RequestBody @Valid Ingredient ingredient,
                                                      @PathVariable long recipeId){

        ingredientService.addRecipeIngredient(ingredient, recipeId);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
}
