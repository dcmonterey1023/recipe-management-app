package com.recipe.recipemanagementapp.exception;

public class CategoryAlreadyExistException extends RuntimeException {

    public CategoryAlreadyExistException(String message){
        super(message);
    }

}
