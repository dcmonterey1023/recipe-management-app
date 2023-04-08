package com.recipe.recipemanagementapp.dto;

import com.recipe.recipemanagementapp.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private List<Category> categories = new ArrayList<>();
    private int count;

}
