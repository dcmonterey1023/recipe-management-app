package com.recipe.recipemanagementapp.repository;

import com.recipe.recipemanagementapp.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    public void deleteAllByRecipeId(long id);

}
