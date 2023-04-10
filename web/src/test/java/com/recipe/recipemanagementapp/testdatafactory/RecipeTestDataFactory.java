package com.recipe.recipemanagementapp.testdatafactory;

import com.recipe.recipemanagementapp.dto.RecipeResponse;
import com.recipe.recipemanagementapp.dto.RecipeSearchRequest;
import com.recipe.recipemanagementapp.entity.*;

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
                .instructions(getRecipeInstruction())
                .ingredients(getSetOfIngredients())
                .prep_time(10)
                .prep_time_unit("minutes")
                .cook_time(1)
                .cook_time_unit("hours")
                .serving(4)
                .nutritions(getSetOfNutrients())
                .notes("Test note")
                .build();
    }

    public static Set<Ingredient> getSetOfIngredients(){
        return Set.of(
                Ingredient.builder().name("pork belly").unitOfMeasure("lbs").amount(2).build(),
                Ingredient.builder().name("garlic").unitOfMeasure("tablespoon").amount(2).build(),
                Ingredient.builder().name("dried bay leaves").unitOfMeasure("pc").amount(5).build(),
                Ingredient.builder().name("vinegar").unitOfMeasure("tablespoon").amount(4).build(),
                Ingredient.builder().name("soy sauce").unitOfMeasure("cup").amount(0.5).build(),
                Ingredient.builder().name("peppercorn").unitOfMeasure("tablespoon").amount(1).build(),
                Ingredient.builder().name("water").unitOfMeasure("cup").amount(2).build(),
                Ingredient.builder().name("salt").unitOfMeasure("tablespoon").amount(0.25).build()
        );
    }

    public static Set<Ingredient> createDuplicateIngredient(){
        return Set.of(
                Ingredient.builder().name("pork belly").unitOfMeasure("lbs").amount(2).build(),
                Ingredient.builder().name("pork belly").unitOfMeasure("kilo").amount(2).build()
        );
    }

    public static Set<Ingredient> createIngredientInvalidUnitOfMeasure(){
        return Set.of(
                Ingredient.builder().name("pork belly").unitOfMeasure("bucket").amount(2).build()
        );
    }

    public static Set<Nutrition> getSetOfNutrients(){
        return Set.of(
                Nutrition.builder().name("Protein").percent(0.01).build(),
                Nutrition.builder().name("Vitamin A").percent(0.009).build(),
                Nutrition.builder().name("Vitamin B").percent(0.2).build()
        );
    }

    public static RecipeResponse getRecipeResponse(){
        return RecipeResponse.builder()
                .recipes(List.of(getRecipeTestData()))
                .count(1)
                .build();
    }

    public static Recipe createRecipeTestData(){
        return getRecipeTestData();
    }

    public static Set<Category> createCategories(){

        return Set.of(
            createCategory(),
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

    public static Set<Instruction> getRecipeInstruction(){
        return Set.of(
                Instruction.builder().step_order(1).instruction("Cut the pork in cubes.").build(),
                Instruction.builder().step_order(1).instruction("Season the pork.").build()
        );
    }

    public static Category createCategory(){
        return Category.builder().name("VEGETARIAN")
                .description("""
                        Vegetarian diet does not include any meat, poultry, or seafood.
                        It is a meal plan made up of foods that come mostly from plants.""")
                .build();
    }

    public static RecipeSearchRequest getRecipeSearchRequest(){
        return RecipeSearchRequest.builder()
                .category("VEGETARIAN")
                .instruction("Season")
                .ingredientExclude("onion")
                .ingredientInclude("pok")
                .serving("4")
                .build();
    }
}
