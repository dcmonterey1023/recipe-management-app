package com.recipe.recipemanagementapp.repository;

import com.recipe.recipemanagementapp.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    public List<Ingredient> findAllByRecipeId(long recipeId);

}
