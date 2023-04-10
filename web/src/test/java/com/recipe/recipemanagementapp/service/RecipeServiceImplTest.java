package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.exception.InvalidRecipeException;
import com.recipe.recipemanagementapp.exception.RecipeAlreadyExistException;
import com.recipe.recipemanagementapp.exception.RecipeNotFoundException;
import com.recipe.recipemanagementapp.exception.RecipeSearchException;
import com.recipe.recipemanagementapp.repository.RecipeRepository;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory.getRecipeSearchRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {
    RecipeService recipeService;
    @Mock
    CategoryService categoryService;
    @Mock
    IngredientService ingredientService;
    @Mock
    NutritionService nutritionService;
    @Mock
    InstructionService instructionService;
    @Mock
    RecipeRepository recipeRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(
                recipeRepository,
                categoryService,
                ingredientService,
                nutritionService,
                instructionService);
    }

    @Test
    @DisplayName("Get All Recipe returns size 1")
    void getAllRecipe() {
        //given
        RecipeResponse recipe = RecipeTestDataFactory.getRecipeResponse();
        //when
        when(recipeRepository.findAll()).thenReturn(recipe.recipes());
        RecipeResponse response = recipeService.getAllRecipe();
        //then
        assertAll(
                () -> assertEquals(response.recipes().size(), 1),
                () -> assertEquals(response.recipes().get(0).getName(), "Pinoy Adobo"),
                () -> assertEquals(response.recipes().size(), 1),
                () -> verify(recipeRepository, times(1)).findAll()
        );
    }

    @Test
    @DisplayName("Get All Recipe no record returns size 0")
    void getAllRecipeNoRecord() {
        //given
        RecipeResponse recipe = new RecipeResponse();
        //when
        when(recipeRepository.findAll()).thenReturn(new ArrayList<>());
        RecipeResponse response = recipeService.getAllRecipe();
        //then
        assertAll(
                () -> assertEquals(response.recipes().size(), 0),
                () -> assertEquals(response.count(), 0),
                () -> verify(recipeRepository, times(1)).findAll()
        );
    }

    @Test
    @DisplayName("Get Recipe By Id 1 returns recipe")
    void getRecipeByIdReturnSuccessful() {
        //given
        Recipe recipe = RecipeTestDataFactory.getRecipeTestData();
        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(recipe));
        Recipe response = recipeService.getRecipeById(1);
        //then
        assertEquals(response.getName(), "Pinoy Adobo");
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Get Recipe by Id using non-existing Id throws exception")
    void getRecipeByIdNotFound() {
        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(RecipeNotFoundException.class, ()-> recipeService.getRecipeById(anyLong()));
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void searchRecipe() {
        //given
        RecipeResponse recipeResponse = RecipeTestDataFactory.getRecipeResponse();
        RecipeSearchRequest recipeSearchRequest = getRecipeSearchRequest();
        //when
        when(recipeRepository.findAllRecipeWithFilter(anyString(),
                anyString(), anyString(), anyString(), anyInt())).thenReturn(recipeResponse.recipes());
        RecipeResponse response = recipeService.searchRecipe(recipeSearchRequest);
        //then
        assertAll(
                () -> assertEquals(response.recipes().size(), 1),
                () -> assertEquals(response.recipes().get(0).getName(), "Pinoy Adobo"),
                () -> assertEquals(response.count(), 1),
                () -> verify(recipeRepository, times(1)).findAllRecipeWithFilter(anyString(),
                        anyString(), anyString(), anyString(), anyInt())
        );
    }

    @Test
    void searchRecipeWithInvalidServing() {
        //given
        RecipeSearchRequest recipeSearchRequest = getRecipeSearchRequest();
        recipeSearchRequest.setServing("invalid");
        //when
        //then
        assertThrows(RecipeSearchException.class, () -> recipeService.searchRecipe(recipeSearchRequest));
        verify(recipeRepository, times(0)).findAllRecipeWithFilter(anyString(),
                        anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    void createRecipe() {
        //given
        Recipe recipe = RecipeTestDataFactory.getRecipeTestData();
        //when
        when(recipeRepository.save(any())).thenReturn(recipe);
        recipeService.createRecipe(recipe);
        //then
        verify(recipeRepository, times(1)).findByName(anyString());
        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void createRecipeInvalidUnitOfTime() {
        //given
        Recipe recipe = RecipeTestDataFactory.getRecipeTestData();
        recipe.setCook_time_unit("hour");
        //then
        verify(recipeRepository, times(0)).findById(1L);
        verify(recipeRepository, times(0)).save(recipe);
        assertThrows(InvalidRecipeException.class, () -> recipeService.createRecipe(recipe));
    }

    @Test
    void createRecipeAlreadyExist() {
        //given
        Recipe recipe = RecipeTestDataFactory.getRecipeTestData();
        when(recipeRepository.findByName(anyString())).thenReturn(Optional.of(recipe));
        //then
        assertThrows(RecipeAlreadyExistException.class, () -> recipeService.createRecipe(recipe));
        verify(recipeRepository, times(1)).findByName(anyString());
        verify(recipeRepository, times(0)).save(recipe);
    }

    @Test
    void createRecipes() {
        //given
        List<Recipe> recipes = List.of(RecipeTestDataFactory.getRecipeTestData());
        //when
        when(recipeRepository.saveAll(any())).thenReturn(recipes);
        recipeService.createRecipes(recipes);
        //then
        verify(recipeRepository, times(1)).saveAll(recipes);
    }

    @Test
    void deleteRecipeById() {
        //given an existing recipe with Id 1
        Recipe recipe = RecipeTestDataFactory.getRecipeTestData();
        //when
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        recipeService.deleteRecipeById(1L);
        //then
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteRecipeByIdNoRecord() {
        //given
        //when
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        assertThrows(RecipeNotFoundException.class, () -> recipeService.deleteRecipeById(1L));
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeRepository, times(0)).deleteById(1L);

    }

    @Test
    void updateRecipeById() {
        //given
        Recipe recipe = RecipeTestDataFactory.getRecipeTestData();
        Recipe updatedRecipe = RecipeTestDataFactory.getRecipeTestData();
        updatedRecipe.setDescription("description updated");
        //when
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(updatedRecipe)).thenReturn(updatedRecipe);
        recipeService.updateRecipeById(updatedRecipe, 1L);
        //then
        assertNotEquals(recipe.getDescription(), updatedRecipe.getDescription());
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeRepository, times(1)).save(updatedRecipe);
    }

    @Test
    void updateRecipeByIdNoRecord() {
        //given
        Recipe updatedRecipe = RecipeTestDataFactory.getRecipeTestData();
        updatedRecipe.setDescription("description updated");
        //when
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        assertThrows(RecipeNotFoundException.class, () -> recipeService.updateRecipeById(updatedRecipe, 1L));
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeRepository, times(0)).save(updatedRecipe);
    }
}