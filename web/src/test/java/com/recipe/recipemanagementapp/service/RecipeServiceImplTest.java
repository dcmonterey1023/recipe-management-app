package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.constants.Categories;
import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.exception.RecipeAlreadyExistException;
import com.recipe.recipemanagementapp.exception.RecipeNotFoundException;
import com.recipe.recipemanagementapp.mapper.RecipeMapper;
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
    private RecipeService recipeService;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private NutritionService nutritionService;
    @Mock
    private InstructionService instructionService;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeMapper mapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(
                recipeRepository,
                ingredientService,
                nutritionService,
                instructionService,
                mapper);
    }

    @Test
    @DisplayName("Get Recipe By Id 1 returns recipe")
    void getRecipeById_successful() {
        //given
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeDtoTestData();
        Recipe recipeEntity = RecipeTestDataFactory.createRecipeEntityResponseTestData();
        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(recipeEntity));
        when(mapper.mapRecipeToRecipeDto(recipeEntity)).thenReturn(recipeDto);
        RecipeDto response = recipeService.getRecipeById(1);
        //then
        assertEquals("Pinoy Adobo", response.getName());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).mapRecipeToRecipeDto(recipeEntity);
    }

    @Test
    @DisplayName("Get Recipe by Id using non-existing Id throws exception")
    void getRecipeById_recipe_not_found() {
        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(RecipeNotFoundException.class, ()-> recipeService.getRecipeById(anyLong()));
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(mapper, times(0)).mapRecipeToRecipeDto(any(Recipe.class));
    }

    @Test
    void searchRecipe_successful() {
        //given
        RecipeSearchRequest recipeSearchRequest = getRecipeSearchRequest();
        Recipe recipeEntity = RecipeTestDataFactory.createRecipeEntityResponseTestData();
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeTestData();
        //TODO
        List<Recipe> recipes = List.of(recipeEntity);
        //when
        when(recipeRepository.findAllRecipeWithFilter(any(Categories.class),
                anyString(), anyString(), anyString(), anyInt())).thenReturn(recipes);
        when(mapper.mapRecipeToRecipeDto(recipeEntity)).thenReturn(recipeDto);
        RecipeResponse response = recipeService.searchRecipes(recipeSearchRequest);
        //then
        assertAll(
                () -> assertEquals(1, response.recipes().size()),
                () -> assertEquals("Pinoy Adobo", response.recipes().get(0).getName()),
                () -> assertEquals(1, response.count()),
                () -> verify(recipeRepository, times(1)).findAllRecipeWithFilter(
                        any(Categories.class),anyString(), anyString(), anyString(), anyInt()),
                () -> verify(mapper, times(1)).mapRecipeToRecipeDto(recipeEntity)
        );
    }

    @Test
    void searchRecipe_no_record_found() {
        //given
        RecipeSearchRequest recipeSearchRequest = getRecipeSearchRequest();
        Recipe recipeEntity = RecipeTestDataFactory.createRecipeEntityResponseTestData();
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeTestData();
        //TODO
        List<Recipe> recipes = List.of(recipeEntity);
        //when
        when(recipeRepository.findAllRecipeWithFilter(any(Categories.class),
                anyString(), anyString(), anyString(), anyInt())).thenReturn(new ArrayList<>());
        RecipeResponse response = recipeService.searchRecipes(recipeSearchRequest);
        //then
        assertAll(
                () -> assertEquals(0, response.recipes().size()),
                () -> assertEquals(0, response.count()),
                () -> verify(recipeRepository, times(1)).findAllRecipeWithFilter(
                        any(Categories.class),anyString(), anyString(), anyString(), anyInt()),
                () -> verify(mapper, times(0)).mapRecipeToRecipeDto(recipeEntity)
        );
    }

    @Test
    void createRecipe_successful() {
        //given
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeDtoTestData();
        Recipe recipeEntity = RecipeTestDataFactory.createRecipeEntityResponseTestData();
        //when
        when(recipeRepository.save(recipeEntity)).thenReturn(recipeEntity);
        when(mapper.mapRecipeDtoToRecipe(recipeDto)).thenReturn(recipeEntity);
        when(mapper.mapRecipeToRecipeDto(recipeEntity)).thenReturn(recipeDto);
        RecipeDto recipeDtoResponse = recipeService.createRecipe(recipeDto);
        //then
        assertEquals("Pinoy Adobo", recipeDtoResponse.getName());
        verify(recipeRepository, times(1)).findByName(anyString());
        verify(recipeRepository, times(1)).save(recipeEntity);
        verify(mapper, times(1)).mapRecipeDtoToRecipe(any(RecipeDto.class));
        verify(mapper, times(1)).mapRecipeToRecipeDto(any(Recipe.class));
    }

    @Test
    void createRecipe_recipe_already_exist() {
        //given
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeDtoTestData();
        Recipe recipeEntity = RecipeTestDataFactory.createRecipeEntityResponseTestData();
        when(recipeRepository.findByName(anyString())).thenReturn(Optional.of(recipeEntity));
        //then
        assertThrows(RecipeAlreadyExistException.class, () -> recipeService.createRecipe(recipeDto));
        verify(recipeRepository, times(1)).findByName(anyString());
        verify(recipeRepository, times(0)).save(recipeEntity);
        verify(mapper, times(0)).mapRecipeDtoToRecipe(any(RecipeDto.class));
        verify(mapper, times(0)).mapRecipeToRecipeDto(any(Recipe.class));
    }
    @Test
    void deleteRecipeById_successful() {
        //given an existing recipe with Id 1
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeDtoTestData();
        //TODO
        Recipe recipeEntity = new Recipe();
        //when
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipeEntity));
        recipeService.deleteRecipeById(1L);
        //then
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteRecipeById_recipe_not_found() {
        //given
        //when
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        assertThrows(RecipeNotFoundException.class, () -> recipeService.deleteRecipeById(1L));
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeRepository, times(0)).deleteById(1L);

    }

    @Test
    void updateRecipeById_successful() {
        //given
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeDtoTestData();
        Recipe recipeEntity = RecipeTestDataFactory.createRecipeEntityResponseTestData();
        recipeDto.setDescription("description updated");
        //when
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipeEntity));
        when(recipeRepository.save(recipeEntity)).thenReturn(recipeEntity);
        when(mapper.mapRecipeDtoToRecipe(recipeDto)).thenReturn(recipeEntity);
        when(mapper.mapRecipeToRecipeDto(recipeEntity)).thenReturn(recipeDto);
        RecipeDto recipeDtoResponse = recipeService.updateRecipeById(recipeDto, 1L);
        //then
        assertNotEquals(recipeDtoResponse.getDescription(), recipeEntity.getDescription());
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeRepository, times(1)).save(recipeEntity);
        verify(mapper, times(1)).mapRecipeDtoToRecipe(recipeDto);
        verify(mapper, times(1)).mapRecipeToRecipeDto(recipeEntity);
    }

    @Test
    void updateRecipeById_no_record_found() {
        //given
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeDtoTestData();
        recipeDto.setDescription("description updated");
        //when
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        assertThrows(RecipeNotFoundException.class, () -> recipeService.updateRecipeById(recipeDto, 1L));
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeRepository, times(0)).save(any(Recipe.class));
        verify(mapper, times(0)).mapRecipeDtoToRecipe(recipeDto);
        verify(mapper, times(0)).mapRecipeToRecipeDto(any(Recipe.class));
    }
}