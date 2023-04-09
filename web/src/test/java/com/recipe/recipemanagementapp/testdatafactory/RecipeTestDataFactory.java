package com.recipe.recipemanagementapp.testdatafactory;

import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.entity.Category;
import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Nutrition;
import com.recipe.recipemanagementapp.entity.Recipe;
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
                .prep_time(1)
                .prep_time_unit("hour")
                .cook_time(1)
                .cook_time_unit("hour")
                .serving(1)
                .nutritions(Set.of(
                        Nutrition.builder().name("Protein").percent(0.01).build(),
                        Nutrition.builder().name("Vitamin A").percent(0.009).build(),
                        Nutrition.builder().name("Vitamin B").percent(0.2).build()
                        )
                )
                .notes("Sample note")
                .build();
    }

    public static Ingredient getIngredientTestData(){
        return Ingredient.builder().name("chicken thigh").unitOfMeasure("kilo").amount(1).build();
    }

    public static Set<Ingredient> getSetOfIngredients(){
        return Set.of(
                Ingredient.builder().name("pork belly").unitOfMeasure("lbs").amount(2).build(),
                Ingredient.builder().name("garlic").unitOfMeasure("tablespoon").amount(2).build(),
                Ingredient.builder().name("dried bay leaves").unitOfMeasure("pcs").amount(5).build(),
                Ingredient.builder().name("vinegar").unitOfMeasure("tablespoon").amount(4).build(),
                Ingredient.builder().name("soy sauce").unitOfMeasure("cup").amount(0.5).build(),
                Ingredient.builder().name("peppercorn").unitOfMeasure("tablespoon").amount(1).build(),
                Ingredient.builder().name("water").unitOfMeasure("cup").amount(2).build(),
                Ingredient.builder().name("salt").unitOfMeasure("tablespoon").amount(0.25).build()
        );
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

        return Set.of(
            Category.builder().name("VEGETARIAN")
                    .description("""
                        Vegetarian diet does not include any meat, poultry, or seafood.
                        It is a meal plan made up of foods that come mostly from plants.""")
                    .build(),
            Category.builder().name("PESCATARIAN")
                    .description("Pescatarian diet is the practice of incorporating seafood into an otherwise vegetarian diet.")
                    .build(),
            Category.builder().name("KETOGENIC")
                    .description("""
                        The ketogenic diet involves consuming a very low amount of carbohydrates
                        and replacing them with fat to help your body burn fat for energy""")
                    .build()
        );
    }
}
