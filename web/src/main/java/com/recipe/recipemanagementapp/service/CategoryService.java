package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.CategoryResponse;
import com.recipe.recipemanagementapp.entity.Category;

import java.util.List;

public interface CategoryService {

    public CategoryResponse getAllCategory();

    public void addCategory(Category category);
    public void addMultipleCategory(List<Category> categories);

    public void validateCategory(String categoryName);

}
