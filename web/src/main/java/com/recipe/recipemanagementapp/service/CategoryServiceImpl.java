package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.CategoryResponse;
import com.recipe.recipemanagementapp.exception.CategoryAlreadyExistException;
import com.recipe.recipemanagementapp.exception.InvalidRecipeCategoryException;
import com.recipe.recipemanagementapp.model.Category;
import com.recipe.recipemanagementapp.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse getAllCategory() {
        var categoryResponse = new CategoryResponse();
        categoryRepository.findAll()
                .forEach(category -> categoryResponse.getCategories().add(category));
        categoryResponse.setCount(categoryResponse.getCategories().size());
        return categoryResponse;
    }

    @Override
    public void addRecipeCategory(Category category) {
        var optionalCategory = categoryRepository.findByName(category.getName());
        if(optionalCategory.isPresent()){
            throw new CategoryAlreadyExistException(
                    String.format("Recipe Category %s already exist. ", category.getName())
            );
        }
        categoryRepository.save(category);
    }

    @Override
    public void validateCategory(String categoryName) {
        if(categoryRepository.findByName(categoryName).isEmpty()){
            throw new InvalidRecipeCategoryException(
                    String.format("Category %s not found", categoryName));
        }
    }
}
