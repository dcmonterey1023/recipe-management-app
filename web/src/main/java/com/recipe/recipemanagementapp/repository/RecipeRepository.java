package com.recipe.recipemanagementapp.repository;

import com.recipe.recipemanagementapp.constants.Categories;
import com.recipe.recipemanagementapp.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    public Optional<Recipe> findByName(String name);

    @Query("""
            select recipe from Recipe recipe
            where (:category is null or recipe.category = :category)
            and (:serving is null or recipe.serving = :serving)
            and (:instruction is null or recipe.id in (select ins.recipe from Instruction ins where instruction like %:instruction%))
            and (:include is null or recipe.id in (select ing.recipe from Ingredient ing where name like %:include%))
            and (:exclude is null or recipe.id not in (select ing.recipe from Ingredient ing where name like %:exclude%))
            """)
    public List<Recipe> findAllRecipeWithFilter(Categories category,
                                                String instruction,
                                                String include,
                                                String exclude,
                                                Integer serving);

}
