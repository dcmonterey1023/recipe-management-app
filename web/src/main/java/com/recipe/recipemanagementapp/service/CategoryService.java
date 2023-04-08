package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.CategoryResponse;
import com.recipe.recipemanagementapp.entity.Category;

public interface CategoryService {

    public CategoryResponse getAllCategory();

    public void addRecipeCategory(Category category);

    public void validateCategory(String categoryName);

}
