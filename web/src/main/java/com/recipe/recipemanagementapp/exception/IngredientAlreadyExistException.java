package com.recipe.recipemanagementapp.exception;

public class IngredientAlreadyExistException extends RuntimeException {

    public IngredientAlreadyExistException(String message){
        super(message);
    }

}
