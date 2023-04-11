package com.recipe.recipemanagementapp.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record RecipeResponse(List<RecipeDto> recipes, int count) {}
