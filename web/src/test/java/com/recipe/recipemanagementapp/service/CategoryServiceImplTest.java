package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.CategoryResponse;
import com.recipe.recipemanagementapp.entity.Category;
import com.recipe.recipemanagementapp.exception.CategoryAlreadyExistException;
import com.recipe.recipemanagementapp.exception.InvalidRecipeCategoryException;
import com.recipe.recipemanagementapp.repository.CategoryRepository;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    CategoryService categoryService;
    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void getAllCategory() {
        //given
        List<Category> categories = RecipeTestDataFactory.createCategories()
                .stream().toList();
        //when
        when(categoryRepository.findAll()).thenReturn(categories);
        CategoryResponse categoryResponse = categoryService.getAllCategory();
        //then
        assertEquals(categoryResponse.getCategories().size(), 3);
        assertEquals(categoryResponse.getCount(), 3);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getAllCategoryNoRecord() {
        //given
        //when
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        CategoryResponse categoryResponse = categoryService.getAllCategory();
        //then
        assertEquals(categoryResponse.getCategories().size(), 0);
        assertEquals(categoryResponse.getCount(), 0);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void addCategory() {
        //given
        Category category = RecipeTestDataFactory.createCategory();
        //when
        when(categoryRepository.save(category)).thenReturn(category);
        categoryService.addCategory(category);
        //then
        verify(categoryRepository, times(1)).save(any());
    }
    @Test
    void addExistingCategory() {
        //given
        Category category = RecipeTestDataFactory.createCategory();
        //when
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        //then
        assertThrows(CategoryAlreadyExistException.class, () -> categoryService.addCategory(category));
        verify(categoryRepository, times(1)).findByName(anyString());
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    void addMultipleCategory() {
        //given
        List<Category> categories = RecipeTestDataFactory.createCategories()
                .stream().toList();
        //when
        when(categoryRepository.saveAll(categories)).thenReturn(categories);
        categoryService.addMultipleCategory(categories);
        //then
        verify(categoryRepository, times(1)).saveAll(any());
    }

    @Test
    void validateCategory() {
        //given
        Category category = RecipeTestDataFactory.createCategory();
        //when
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        categoryService.validateCategory(anyString());
        //then
        verify(categoryRepository, times(1)).findByName(anyString());
    }

    @Test
    void validateCategoryFail() {
        //given
        //when
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(InvalidRecipeCategoryException.class,
                () -> categoryService.validateCategory(anyString()));
        verify(categoryRepository, times(1)).findByName(anyString());
    }
}