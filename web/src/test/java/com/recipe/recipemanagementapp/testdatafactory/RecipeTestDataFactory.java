package com.recipe.recipemanagementapp.testdatafactory;

import com.recipe.recipemanagementapp.constants.Categories;
import com.recipe.recipemanagementapp.constants.Cuisines;
import com.recipe.recipemanagementapp.constants.UnitOfMeasure;
import com.recipe.recipemanagementapp.dto.*;
import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Instruction;
import com.recipe.recipemanagementapp.entity.Nutrition;
import com.recipe.recipemanagementapp.entity.Recipe;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

public class RecipeTestDataFactory {
    public static RecipeDto createRecipeDtoTestData(){
        return RecipeDto.builder()
                .id(1)
                .name("Pinoy Adobo")
                .description("Pinoy staple food")
                .category("VEGETARIAN")
                .cuisine("FILIPINO")
                .instructions(getRecipeInstruction())
                .ingredients(getSetOfIngredients())
                .prepTime(10)
                .prepTimeUnit("minutes")
                .cookTime(1)
                .cookTimeUnit("hours")
                .serving(4)
                .nutritions(getSetOfNutrients())
                .notes("Test note")
                .build();
    }

    public static Recipe createRecipeEntityResponseTestData(){
        return Recipe.builder()
                .id(1)
                .name("Pinoy Adobo")
                .description("Pinoy staple food")
                .category(Categories.VEGETARIAN)
                .cuisine(Cuisines.FILIPINO)
                .instructions(createInstructionEntityTestData())
                .ingredients(createIngredientEntityTestData())
                .prepTime(10)
                .prepTimeUnit(ChronoUnit.MINUTES)
                .cookTime(1)
                .cookTimeUnit(ChronoUnit.HOURS)
                .serving(4)
                .nutritions(getNutritionEntityTestData())
                .notes("Test note")
                .build();
    }

    public static Set<IngredientDto> getSetOfIngredients(){
        return Set.of(
                IngredientDto.builder().name("pork belly").unitOfMeasure("lbs").amount(2).build(),
                IngredientDto.builder().name("garlic").unitOfMeasure("tablespoon").amount(2).build(),
                IngredientDto.builder().name("dried bay leaves").unitOfMeasure("pc").amount(5).build(),
                IngredientDto.builder().name("vinegar").unitOfMeasure("tablespoon").amount(4).build(),
                IngredientDto.builder().name("soy sauce").unitOfMeasure("cup").amount(0.5).build(),
                IngredientDto.builder().name("peppercorn").unitOfMeasure("tablespoon").amount(1).build(),
                IngredientDto.builder().name("water").unitOfMeasure("cup").amount(2).build(),
                IngredientDto.builder().name("salt").unitOfMeasure("tablespoon").amount(0.25).build()
        );
    }

    public static Set<Ingredient> createIngredientEntityTestData(){
        return Set.of(
                Ingredient.builder().id(1).name("pork belly").unitOfMeasure(UnitOfMeasure.LBS).amount(2).build(),
                Ingredient.builder().id(2).name("garlic").unitOfMeasure(UnitOfMeasure.TABLESPOON).amount(2).build(),
                Ingredient.builder().id(3).name("dried bay leaves").unitOfMeasure(UnitOfMeasure.PC).amount(5).build(),
                Ingredient.builder().id(4).name("vinegar").unitOfMeasure(UnitOfMeasure.TABLESPOON).amount(4).build(),
                Ingredient.builder().id(5).name("soy sauce").unitOfMeasure(UnitOfMeasure.CUP).amount(0.5).build(),
                Ingredient.builder().id(6).name("peppercorn").unitOfMeasure(UnitOfMeasure.TABLESPOON).amount(1).build(),
                Ingredient.builder().id(7).name("water").unitOfMeasure(UnitOfMeasure.CUP).amount(2).build(),
                Ingredient.builder().id(8).name("salt").unitOfMeasure(UnitOfMeasure.TABLESPOON).amount(0.25).build()
        );
    }

    public static IngredientDto createSingleIngredientDtoTestData(){
        return IngredientDto.builder().name("pork belly").unitOfMeasure("lbs").amount(2).build();
    }
    public static Ingredient createSingleIngredientEntityTestData(){
        return Ingredient.builder().id(1).name("pork belly").unitOfMeasure(UnitOfMeasure.LBS).amount(2).build();
    }

    public static Set<IngredientDto> createDuplicateIngredient(){
        return Set.of(
                IngredientDto.builder().name("pork belly").unitOfMeasure("lbs").amount(2).build(),
                IngredientDto.builder().name("pork belly").unitOfMeasure("kilo").amount(2).build()
        );
    }

    public static Set<NutritionDto> getSetOfNutrients(){
        return Set.of(
                NutritionDto.builder().name("Protein").percent(0.01).build(),
                NutritionDto.builder().name("Vitamin A").percent(0.009).build(),
                NutritionDto.builder().name("Vitamin B").percent(0.2).build()
        );
    }

    public static Set<Nutrition> getNutritionEntityTestData(){
        return Set.of(
                Nutrition.builder().name("Protein").percent(0.01).build(),
                Nutrition.builder().name("Vitamin A").percent(0.009).build(),
                Nutrition.builder().name("Vitamin B").percent(0.2).build()
        );
    }
    public static Nutrition getSingleNutritionEntityTestData(){
        return Nutrition.builder().id(1).name("Protein").percent(0.01).build();
    }

    public static NutritionDto getNutritionDtoTestData(){
        return NutritionDto.builder().name("Protein").percent(0.01).build();
    }

    public static RecipeResponse getRecipeResponse(){
        return RecipeResponse.builder()
                .recipes(List.of(createRecipeDtoTestData()))
                .count(1)
                .build();
    }

    public static RecipeDto createRecipeTestData(){
        return createRecipeDtoTestData();
    }
    public static Set<InstructionDto> getRecipeInstruction(){
        return Set.of(
                InstructionDto.builder().stepOrder(1).instruction("Cut the pork in cubes.").build(),
                InstructionDto.builder().stepOrder(2).instruction("Season the pork.").build()
        );
    }

    public static Set<Instruction> createInstructionEntityTestData(){
        return Set.of(
                Instruction.builder().id(1).stepOrder(1).instruction("Cut the pork in cubes.").build(),
                Instruction.builder().id(2).stepOrder(2).instruction("Season the pork.").build()
        );
    }

    public static Instruction createSingleInstructionEntityTestData(){
        return Instruction.builder().id(1).stepOrder(1).instruction("Cut the pork in cubes.").build();
    }

    public static InstructionDto createSingleInstructionDtoTestData(){
        return InstructionDto.builder().stepOrder(1).instruction("Cut the pork in cubes.").build();
    }

    public static RecipeSearchRequest getRecipeSearchRequest(){
        return RecipeSearchRequest.builder()
                .category(Categories.VEGETARIAN)
                .instruction("Season")
                .ingredientExclude("onion")
                .ingredientInclude("pok")
                .serving(4)
                .build();
    }

    public static RecipeDto createRecipeDtoAllFieldsInvalid(){
        return RecipeDto.builder()
                .name("")
                .description("")
                .category("")
                .cookTimeUnit("")
                .cookTime(0)
                .cuisine("")
                .ingredients(Set.of())
                .instructions(Set.of())
                .nutritions(Set.of())
                .prepTime(0)
                .prepTimeUnit("")
                .serving(0)
                .notes("")
                .build();
    }
}
