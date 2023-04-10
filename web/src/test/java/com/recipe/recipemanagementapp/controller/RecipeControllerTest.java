package com.recipe.recipemanagementapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.recipemanagementapp.RecipeManagementApp;
import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.exception.IngredientAlreadyExistException;
import com.recipe.recipemanagementapp.exception.RecipeAlreadyExistException;
import com.recipe.recipemanagementapp.exception.RecipeAppAdvice;
import com.recipe.recipemanagementapp.exception.RecipeNotFoundException;
import com.recipe.recipemanagementapp.service.RecipeService;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
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
    void getRecipe() throws Exception {
        RecipeResponse recipeResponse = RecipeTestDataFactory.getRecipeResponse();
        when(recipeService.getAllRecipe()).thenReturn(recipeResponse);
        this.mvc
                .perform(get("/recipe"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.recipes[0].name").value("Pinoy Adobo"));
    }
    @Test
    void getRecipeNoRecord() throws Exception {
        when(recipeService.getAllRecipe()).thenReturn(new RecipeResponse());
        this.mvc
                .perform(get("/recipe"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.recipes").isEmpty());
    }
    @Test
    void getRecipeById() throws Exception {
        Recipe recipe = RecipeTestDataFactory.createRecipeTestData();
        when(recipeService.getRecipeById(1L)).thenReturn(recipe);
        this.mvc
                .perform(get("/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Pinoy Adobo"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getRecipeById_NotFound() throws Exception {
        long id = 1;
        RecipeNotFoundException exception = new RecipeNotFoundException(
                String.format("Recipe with id %d not found", id));
        when(recipeService.getRecipeById(id)).thenThrow(exception);
        this.mvc
                .perform(get("/recipe/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Recipe with id 1 not found"));
    }

    @Test
    void searchRecipe() throws Exception {
        RecipeResponse recipeResponse = RecipeTestDataFactory.getRecipeResponse();
        when(recipeService.searchRecipe(any(RecipeSearchRequest.class))).thenReturn(recipeResponse);
        this.mvc
                .perform(get("/recipe/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.recipes[0].name").value("Pinoy Adobo"));
    }

    @Test
    void createRecipe() throws Exception {
        Recipe recipe = RecipeTestDataFactory.createRecipeTestData();
        doNothing().when(recipeService).createRecipe(recipe);

        this.mvc
                .perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Successfully Created"));
    }

    @Test
    void createRecipe_AlreadyExist() throws Exception {
        Recipe recipe = RecipeTestDataFactory.createRecipeTestData();
        RecipeAlreadyExistException exception = new RecipeAlreadyExistException(
                String.format("Recipe %s already exist", recipe.getName())
        );
        doThrow(exception).when(recipeService).createRecipe(recipe);

        this.mvc
                .perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message")
                        .value(String.format("Recipe %s already exist", recipe.getName())));
    }

    @Test
    void createRecipe_duplicate_ingredient() throws Exception {
        Recipe recipe = RecipeTestDataFactory.createRecipeTestData();
        IngredientAlreadyExistException exception = new IngredientAlreadyExistException("Duplicate ingredients found.");
        doThrow(exception).when(recipeService).createRecipe(recipe);

        this.mvc
                .perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Duplicate ingredients found."));
    }

    @Test
    void createRecipes() throws Exception {
        List<Recipe> recipes = List.of(RecipeTestDataFactory.createRecipeTestData());
        doNothing().when(recipeService).createRecipes(recipes);

        this.mvc
                .perform(post("/recipe/all")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipes)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Successfully Created"));
    }

    @Test
    void createRecipes_at_least_one_already_exist() throws Exception {
        List<Recipe> recipes = List.of(RecipeTestDataFactory.createRecipeTestData());
        RecipeAlreadyExistException exception = new RecipeAlreadyExistException(
                String.format("Recipe %s already exist", recipes.get(0).getName())
        );
        doThrow(exception).when(recipeService).createRecipes(recipes);

        this.mvc
                .perform(post("/recipe/all")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipes)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message")
                        .value(String.format("Recipe %s already exist", recipes.get(0).getName())));
    }

    @Test
    void deleteRecipe() throws Exception {
        doNothing().when(recipeService).deleteRecipeById(1L);

        this.mvc
                .perform(delete("/recipe/{recipeId}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully Deleted"));
    }

    @Test
    void deleteRecipe_Not_Existing() throws Exception {
        long id = 1;
        RecipeNotFoundException exception = new RecipeNotFoundException(
                String.format("Can't delete recipe with id %d. Id not found.", id)
        );
        doThrow(exception).when(recipeService).deleteRecipeById(id);

        this.mvc
                .perform(delete("/recipe/{recipeId}", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value(
                        String.format("Can't delete recipe with id %d. Id not found.", id)
                ));
    }

    @Test
    void updateRecipe() throws Exception {
        Recipe recipe = RecipeTestDataFactory.createRecipeTestData();
        doNothing().when(recipeService).updateRecipeById(recipe, 1);

        this.mvc
                .perform(patch("/recipe/{recipeId}", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully Updated"));
    }

    @Test
    void updateRecipe_not_found() throws Exception {
        Recipe recipe = RecipeTestDataFactory.createRecipeTestData();
        long id = 1;
        RecipeNotFoundException exception = new RecipeNotFoundException(
                String.format("Recipe with id %d not found", id));

        doThrow(exception).when(recipeService).updateRecipeById(recipe, 1);

        this.mvc
                .perform(patch("/recipe/{recipeId}", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Recipe with id 1 not found"));
    }
}