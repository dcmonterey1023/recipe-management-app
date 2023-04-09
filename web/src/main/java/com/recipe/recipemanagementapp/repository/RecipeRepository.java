package com.recipe.recipemanagementapp.repository;

import com.recipe.recipemanagementapp.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    public Optional<Recipe> findByName(String name);

    @Query("""
            select recipe from RECIPE_TBL recipe
            where (:category is null or recipe.category = :category)
            and (:instruction is null or recipe.id in (select ins.recipe from INSTRUCTION_TBL ins where instruction like %:instruction%))
            and (:serving < 1 or recipe.serving = :serving)
            and (:include is null or recipe.id in (select ing.recipe from INGREDIENT_TBL ing where name = :include))
            and recipe.id not in (select ing.recipe from INGREDIENT_TBL ing where name = :exclude)
            """)
    public List<Recipe> findAllRecipeWithFilter(String category,
                                                String instruction,
                                                String include,
                                                String exclude,
                                                int serving);

}
