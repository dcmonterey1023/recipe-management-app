package com.recipe.recipemanagementapp.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class RecipeAppErrorResponse {

    private String message;
    private int code;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> errorMessagesMap;

}
