package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.NutritionDto;
import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.entity.Instruction;
import com.recipe.recipemanagementapp.entity.Nutrition;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.mapper.NutritionMapper;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NutritionServiceImplTest {

    private NutritionService nutritionService;
    @Mock
    NutritionMapper mapper;

    @BeforeEach
    void setUp() {
        nutritionService = new NutritionServiceImpl(mapper);
    }

    @Test
    void mapRecipeToNutrient_successful() {
        //given
        Recipe recipeEntity = RecipeTestDataFactory.createRecipeEntityResponseTestData();
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeTestData();
        NutritionDto nutritionDto = RecipeTestDataFactory.getNutritionDtoTestData();
        Nutrition nutrition = RecipeTestDataFactory.getSingleNutritionEntityTestData();
        recipeDto.setNutritions(Set.of(nutritionDto));
        recipeEntity.setNutritions(Set.of(nutrition));
        //when
        when(mapper.mapNutritionDtoToNutrition(nutritionDto)).thenReturn(nutrition);
        Set<Nutrition> nutrients = nutritionService.mapRecipeToNutrient(recipeDto, recipeEntity);
        //then
        assertEquals(recipeDto.getNutritions().size(), nutrients.size());
        verify(mapper, times(1)).mapNutritionDtoToNutrition(nutritionDto);
    }
}