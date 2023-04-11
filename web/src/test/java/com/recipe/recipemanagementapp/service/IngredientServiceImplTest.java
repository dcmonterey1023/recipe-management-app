package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.IngredientDto;
import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.exception.IngredientAlreadyExistException;
import com.recipe.recipemanagementapp.mapper.IngredientMapper;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory.createDuplicateIngredient;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    IngredientService ingredientService;
    @Mock
    IngredientMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(mapper);
    }

    @Test
    void mapRecipeToIngredient_successful() {
        //given
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeDtoTestData();
        Recipe recipeEntity = RecipeTestDataFactory.createRecipeEntityResponseTestData();
        IngredientDto ingredientDto = RecipeTestDataFactory.createSingleIngredientDtoTestData();
        Ingredient ingredient = RecipeTestDataFactory.createSingleIngredientEntityTestData();

        recipeDto.setIngredients(Set.of(ingredientDto));
        recipeEntity.setIngredients(Set.of(ingredient));
        //when
        when(mapper.mapIngredientDtoToIngredient(ingredientDto)).thenReturn(ingredient);
        Set<Ingredient> ingredients = ingredientService.mapRecipeToIngredient(recipeDto, recipeEntity);
        //then
        assertEquals(recipeDto.getIngredients().size(), ingredients.size());
        verify(mapper, times(1)).mapIngredientDtoToIngredient(ingredientDto);
    }

    @Test
    void mapRecipeToIngredient_duplicate_ingredients_exception() {
        //given
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeDtoTestData();
        recipeDto.setIngredients(createDuplicateIngredient());
        Recipe recipeEntity = RecipeTestDataFactory.createRecipeEntityResponseTestData();

        assertThrows(IngredientAlreadyExistException.class,
                () -> ingredientService.mapRecipeToIngredient(recipeDto, recipeEntity));
        verify(mapper, times(0)).mapIngredientDtoToIngredient(any(IngredientDto.class));
    }
}