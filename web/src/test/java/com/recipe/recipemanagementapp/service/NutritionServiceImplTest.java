package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.entity.Instruction;
import com.recipe.recipemanagementapp.entity.Nutrition;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NutritionServiceImplTest {

    NutritionService nutritionService;

    @BeforeEach
    void setUp() {
        nutritionService = new NutritionServiceImpl();
    }

    @Test
    void mapRecipeToNutrient() {
        //given
        Recipe recipe = RecipeTestDataFactory.createRecipeTestData();
        //when
        Set<Nutrition> nutrients = nutritionService.mapRecipeToNutrient(recipe);
        //then
        assertEquals(recipe.getNutritions().size(), nutrients.size());
    }
}