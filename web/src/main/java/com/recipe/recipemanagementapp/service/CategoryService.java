package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.CategoryResponse;
import com.recipe.recipemanagementapp.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    public CategoryResponse getAllCategory();

    public void addRecipeCategory(Category category);

    public void validateCategory(String categoryName);

}
