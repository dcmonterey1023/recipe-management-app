package com.recipe.recipemanagementapp.exception;

public class RecipeAlreadyExistException extends RuntimeException {
    public RecipeAlreadyExistException(String message){
        super(message);
    }
}
