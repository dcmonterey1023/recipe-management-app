package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.CategoryResponse;
import com.recipe.recipemanagementapp.exception.CategoryAlreadyExistException;
import com.recipe.recipemanagementapp.exception.InvalidRecipeCategoryException;
import com.recipe.recipemanagementapp.entity.Category;
import com.recipe.recipemanagementapp.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addCategory(Category category) {
        var optionalCategory = categoryRepository.findByName(category.getName());
        if(optionalCategory.isPresent()){
            throw new CategoryAlreadyExistException(
                    String.format("Recipe Category %s already exist. ", category.getName())
            );
        }
        categoryRepository.save(category);
    }

    @Override
    public void addMultipleCategory(List<Category> categories) {
        categories.forEach(category -> validateCategory(category.getName()));
        categoryRepository.saveAll(categories);
    }

    @Override
    public void validateCategory(String categoryName) {
        if(categoryRepository.findByName(categoryName).isEmpty()){
            throw new InvalidRecipeCategoryException(
                    String.format("Category %s not found", categoryName));
        }
    }
}
