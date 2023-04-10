package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.exception.IngredientAlreadyExistException;
import com.recipe.recipemanagementapp.exception.UnitOfMeasureNotValidException;
import com.recipe.recipemanagementapp.repository.IngredientRepository;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory.createDuplicateIngredient;
import static com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory.createIngredientInvalidUnitOfMeasure;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @Mock
    IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientRepository, unitOfMeasureService);
    }

    @Test
    void mapRecipeToIngredient() {
        //given
        Recipe recipe = RecipeTestDataFactory.getRecipeTestData();
        //when
        doNothing().when(unitOfMeasureService).validateUnitOfMeasure(anyString());
        Set<Ingredient> ingredients = ingredientService.mapRecipeToIngredient(recipe);
        //then
        assertEquals(recipe.getIngredients().size(), ingredients.size());
    }

    @Test
    void mapRecipeToIngredientDuplicateIngredients() {
        //given
        Recipe recipe = RecipeTestDataFactory.getRecipeTestData();
        recipe.setIngredients(createDuplicateIngredient());
        //when
        //lenient().doNothing().when(unitOfMeasureService).validateUnitOfMeasure(anyString());
        //then
        assertThrows(IngredientAlreadyExistException.class, () -> ingredientService.mapRecipeToIngredient(recipe));
        verify(unitOfMeasureService, times(0)).validateUnitOfMeasure(anyString());
    }

    @Test
    void mapRecipeToIngredientInvalidUnitOfMeasure() {
        //given
        Recipe recipe = RecipeTestDataFactory.getRecipeTestData();
        //when
        doThrow(UnitOfMeasureNotValidException.class).when(unitOfMeasureService).validateUnitOfMeasure(anyString());
        //then
        assertThrows(UnitOfMeasureNotValidException.class, () -> ingredientService.mapRecipeToIngredient(recipe));
        verify(unitOfMeasureService, times(1)).validateUnitOfMeasure(anyString());
    }
}