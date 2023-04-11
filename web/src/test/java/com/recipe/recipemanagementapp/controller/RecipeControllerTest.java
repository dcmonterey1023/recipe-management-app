package com.recipe.recipemanagementapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.recipemanagementapp.constants.MessageConstants;
import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.exception.IngredientAlreadyExistException;
import com.recipe.recipemanagementapp.exception.RecipeAlreadyExistException;
import com.recipe.recipemanagementapp.exception.RecipeNotFoundException;
import com.recipe.recipemanagementapp.service.RecipeService;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
@RequiredArgsConstructor
class RecipeControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    RecipeService recipeService;
    RecipeController recipeController;

    @BeforeEach
    void setUp(){
        recipeController = new RecipeController(recipeService);
    }
    @Test
    void getRecipeById_successful() throws Exception {
        RecipeDto recipe = RecipeTestDataFactory.createRecipeTestData();
        when(recipeService.getRecipeById(1L)).thenReturn(recipe);
        this.mvc
                .perform(get("/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Pinoy Adobo"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getRecipeById_recipe_not_found() throws Exception {
        long id = 1;
        RecipeNotFoundException exception = new RecipeNotFoundException(
                String.format("Recipe with id %d not found", id));
        when(recipeService.getRecipeById(id)).thenThrow(exception);
        this.mvc
                .perform(get("/recipes/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Recipe with id 1 not found"));
    }

    @Test
    void searchRecipe_with_1_record() throws Exception {
        RecipeResponse recipeResponse = RecipeTestDataFactory.getRecipeResponse();
        when(recipeService.searchRecipes(any(RecipeSearchRequest.class))).thenReturn(recipeResponse);
        this.mvc
                .perform(get("/recipes/search?category=VEGETARIAN&instruction=cook&serving=4&ingredient&ingredientInclude=garlic&ingredientExclude=onion"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.recipes[0].name").value("Pinoy Adobo"));
    }

    @Test
    void searchRecipe_invalid_serving_filter() throws Exception {
        RecipeResponse recipeResponse = RecipeTestDataFactory.getRecipeResponse();
        when(recipeService.searchRecipes(any(RecipeSearchRequest.class))).thenReturn(recipeResponse);
        this.mvc
                .perform(get("/recipes/search?serving=invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("MethodArgumentNotValidException encountered."))
                .andExpect(jsonPath("$.errorMessagesMap.serving").value("""
                        Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; For input string: "invalid\""""));
    }

    @Test
    void createRecipe_successful() throws Exception {
        RecipeDto recipe = RecipeTestDataFactory.createRecipeTestData();
        when(recipeService.createRecipe(recipe)).thenReturn(recipe);

        this.mvc
                .perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createRecipe_recipe_already_exist() throws Exception {
        RecipeDto recipe = RecipeTestDataFactory.createRecipeTestData();
        RecipeAlreadyExistException exception = new RecipeAlreadyExistException(
                String.format("Recipe %s already exist", recipe.getName())
        );
        doThrow(exception).when(recipeService).createRecipe(recipe);

        this.mvc
                .perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message")
                        .value(String.format("Recipe %s already exist", recipe.getName())));
    }

    @Test
    void createRecipe_duplicate_ingredient() throws Exception {
        RecipeDto recipe = RecipeTestDataFactory.createRecipeTestData();
        IngredientAlreadyExistException exception = new IngredientAlreadyExistException("Duplicate ingredients found.");
        doThrow(exception).when(recipeService).createRecipe(recipe);

        this.mvc
                .perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Duplicate ingredients found."));
    }


    @Test
    void createRecipe_all_required_fields_invalid() throws Exception {

        RecipeDto recipe = RecipeTestDataFactory.createRecipeDtoAllFieldsInvalid();
        when(recipeService.createRecipe(recipe)).thenReturn(recipe);

        this.mvc
                .perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("MethodArgumentNotValidException encountered."))
                .andExpect(jsonPath("$.errorMessagesMap.instructions").value("Instruction must have at least one entry."))
                .andExpect(jsonPath("$.errorMessagesMap.name").value(MessageConstants.NOT_BLANK_MESSAGE))
                .andExpect(jsonPath("$.errorMessagesMap.cookTime").value("Cooking time should not be less than 1."))
                .andExpect(jsonPath("$.errorMessagesMap.ingredients").value("Ingredient must have at least one entry."))
                .andExpect(jsonPath("$.errorMessagesMap.description").value(MessageConstants.NOT_BLANK_MESSAGE))
                .andExpect(jsonPath("$.errorMessagesMap.cuisine").value(MessageConstants.NOT_BLANK_MESSAGE))
                .andExpect(jsonPath("$.errorMessagesMap.category").value(MessageConstants.NOT_BLANK_MESSAGE))
                .andExpect(jsonPath("$.errorMessagesMap.prepTimeUnit").value(MessageConstants.NOT_BLANK_MESSAGE))
                .andExpect(jsonPath("$.errorMessagesMap.cookTimeUnit").value(MessageConstants.NOT_BLANK_MESSAGE))
                .andExpect(jsonPath("$.errorMessagesMap.prepTime").value("Preparation time should not be less than 1."))
                .andExpect(jsonPath("$.errorMessagesMap.serving").value("Serving should not be less than 1."));
    }
    @Test
    void deleteRecipe_successful() throws Exception {
        doNothing().when(recipeService).deleteRecipeById(1L);

        this.mvc
                .perform(delete("/recipes/{recipeId}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully Deleted"));
    }

    @Test
    void deleteRecipe_recipe_not_found() throws Exception {
        long id = 1;
        RecipeNotFoundException exception = new RecipeNotFoundException(
                String.format("Can't delete recipe with id %d. Id not found.", id)
        );
        doThrow(exception).when(recipeService).deleteRecipeById(id);

        this.mvc
                .perform(delete("/recipes/{recipeId}", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value(
                        String.format("Can't delete recipe with id %d. Id not found.", id)
                ));
    }

    @Test
    void updateRecipe_successful() throws Exception {
        RecipeDto recipe = RecipeTestDataFactory.createRecipeTestData();
        when(recipeService.updateRecipeById(recipe, 1)).thenReturn(recipe);

        this.mvc
                .perform(put("/recipes/{recipeId}", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateRecipe_recipe_not_found() throws Exception {
        RecipeDto recipe = RecipeTestDataFactory.createRecipeTestData();
        long id = 1;
        RecipeNotFoundException exception = new RecipeNotFoundException(
                String.format("Recipe with id %d not found", id));

        doThrow(exception).when(recipeService).updateRecipeById(recipe, 1);

        this.mvc
                .perform(put("/recipes/{recipeId}", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Recipe with id 1 not found"));
    }
}