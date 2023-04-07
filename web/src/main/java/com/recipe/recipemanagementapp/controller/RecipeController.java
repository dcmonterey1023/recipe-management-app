package com.recipe.recipemanagementapp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @GetMapping
    public String getRecipe(){
        return "All Recipe";
    }

    @GetMapping("/{recipeId}")
    public String getRecipeById(@PathVariable long recipeId){
        return "Recipe By Id";
    }

    @GetMapping("/search")
    public String searchRecipe(){
        return "Search Recipe";
    }

    @PostMapping
    public String createRecipe(){
        return "Create Recipe";
    }

    @DeleteMapping("/{recipeId}")
    public String deleteRecipe(@PathVariable long recipeId){
        return "Delete recipe by Id";
    }

    @PatchMapping("/{recipeId}")
    public String updateRecipe(@PathVariable long recipeId){
        return "Update Recipe By Id";
    }
}
