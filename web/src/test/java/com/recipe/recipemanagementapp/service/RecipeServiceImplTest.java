package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.model.Recipe;
import com.recipe.recipemanagementapp.repository.IngredientRepository;
import com.recipe.recipemanagementapp.repository.RecipeRepository;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeService recipeService;
    CategoryServiceImpl categoryService;
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientRepository ingredientRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, categoryService, ingredientRepository);
    }

    @Test
    @DisplayName("Get all recipe with 1 recipe only")
    void getAllRecipe() {
        //given recipe of 1 count
        RecipeResponse recipeResponse = RecipeTestDataFactory.getRecipeResponse();
        //when get all recipe is called
        when(recipeService.getAllRecipe()).thenReturn(recipeResponse);

        assertAll(
                () -> assertEquals(recipeService.getAllRecipe().getCount(), 1),
                () -> assertEquals(recipeService.getAllRecipe().getRecipes().get(1).getName(), "Pinoy Adobo")
        );
    }

    @Test
    @DisplayName("Get Recipe with ID of 1")
    void getRecipeById() {
        //given id = 1
        Recipe recipe = RecipeTestDataFactory.getRecipeTestData();
        //when
        when(recipeService.getRecipeById(1)).thenReturn(recipe);
        //then
        assertAll(
                () -> assertEquals(recipeService.getAllRecipe().getCount(), 1),
                () -> assertEquals(recipeService.getAllRecipe().getRecipes().get(1).getName(), "Pinoy Adobo")
        );
    }

    @Test
    void searchRecipe() {
        fail();
    }

    @Test
    @DisplayName("Create Recipe with name Tinolang Manok")
    void createRecipe() {
        //given a request with all correct values;
        Recipe recipe = RecipeTestDataFactory.createRecipeTestData();
        //when
        recipeService.createRecipe(recipe);
        //then
        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void deleteRecipeById() {
        //given
        fail();
        //when

        //then
    }

    @Test
    void updateRecipeById() {
        //given
        fail();
        //when

        //then
    }
}