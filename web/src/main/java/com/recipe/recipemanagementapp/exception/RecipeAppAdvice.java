package com.recipe.recipemanagementapp.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RecipeAppAdvice {

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<RecipeAppErrorResponse> handle(RecipeNotFoundException exception){
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(RecipeAlreadyExistException.class)
    public ResponseEntity<RecipeAppErrorResponse> handle(RecipeAlreadyExistException exception){
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<RecipeAppErrorResponse> handle(CategoryAlreadyExistException exception){
        return new ResponseEntity<>(
          createErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), null),
          HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidRecipeCategoryException.class)
    public ResponseEntity<RecipeAppErrorResponse> handle(InvalidRecipeCategoryException exception){
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UnitOfMeasureNotValidException.class)
    public ResponseEntity<RecipeAppErrorResponse> handle(UnitOfMeasureNotValidException exception){
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(),null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RecipeSearchException.class)
    public ResponseEntity<RecipeAppErrorResponse> handle(RecipeSearchException exception){
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IngredientAlreadyExistException.class)
    public ResponseEntity<RecipeAppErrorResponse> handle(IngredientAlreadyExistException exception){
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidRecipeException.class)
    public ResponseEntity<RecipeAppErrorResponse> handle(InvalidRecipeException exception){
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handle(MethodArgumentNotValidException exception){
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach( error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(
                createErrorResponse("MethodArgumentNotValidException encountered.", HttpStatus.BAD_REQUEST.value(), validationErrors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handle(ConstraintViolationException exception){
        Map<String, String> validationErrors = new HashMap<>();
        exception.getConstraintViolations()
                .forEach( error -> validationErrors.put(error.getPropertyPath().toString(), error.getMessage()));

        return new ResponseEntity<>(
                createErrorResponse("ConstraintViolationException encountered.", HttpStatus.BAD_REQUEST.value(), validationErrors),
                HttpStatus.BAD_REQUEST);
    }

    private RecipeAppErrorResponse createErrorResponse(String message, int code, Map<String, String> map){
        return RecipeAppErrorResponse.builder()
                .message(message)
                .code(code)
                .errorMessagesMap(map)
                .build();
    }
}
