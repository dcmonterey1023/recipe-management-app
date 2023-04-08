package com.recipe.recipemanagementapp.testdatafactory;

import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.model.Category;
import com.recipe.recipemanagementapp.model.Ingredient;
import com.recipe.recipemanagementapp.model.Recipe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeTestDataFactory {

    public static Recipe getRecipeTestData(){
        return Recipe.builder()
                .id(1)
                .name("Pinoy Adobo")
                .description("Pinoy staple food")
                .category("VEGETARIAN")
                .cuisine("FILIPINO")
                .instruction("Saute garlic")
                .ingredients(Set.of(getIngredientTestData()))
                .preparation_time(1)
                .serving(1)
                .notes("Sample note")
                .build();
    }

    public static Ingredient getIngredientTestData(){

        return Ingredient.builder()
                //.id(1)
                .name("Chicken")
                .description("Fresh Chicken")
                .count(1)
                .build();
    }

    public static RecipeResponse getRecipeResponse(){
        return RecipeResponse.builder()
                .recipes(List.of(getRecipeTestData()))
                .build();
    }

    public static Recipe createRecipeTestData(){
        return getRecipeTestData();
    }

    public static Set<Category> createCategories(){
        Set<Category> categories = new HashSet<>();
        categories.add(Category.builder().name("VEGETARIAN").description("All veggies").build());
        categories.add(Category.builder().name("KETO").description("Keto diet").build());

        return categories;
    }


}
