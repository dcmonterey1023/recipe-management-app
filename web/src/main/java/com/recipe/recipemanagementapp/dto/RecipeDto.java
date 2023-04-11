package com.recipe.recipemanagementapp.dto;

import com.recipe.recipemanagementapp.constants.MessageConstants;
import com.recipe.recipemanagementapp.entity.Ingredient;
import com.recipe.recipemanagementapp.entity.Instruction;
import com.recipe.recipemanagementapp.entity.Nutrition;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private long id;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String name;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    @Size(max = 150)
    private String description;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String category;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String cuisine;
    @Min(value = 1, message = "Serving should not be less than 1.")
    private int serving;
    @Min(value = 1, message = "Preparation time should not be less than 1.")
    private int prepTime;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String prepTimeUnit;
    @Min(value = 1, message = "Cooking time should not be less than 1.")
    private int cookTime;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String cookTimeUnit;
    @Valid
    @NotNull
    @Size(min = 1, message = "Ingredient must have at least one entry.")
    private Set<IngredientDto> ingredients = new HashSet<>();
    @Valid
    @NotNull
    @Size(min = 1, message = "Instruction must have at least one entry.")
    private Set<InstructionDto> instructions = new HashSet<>();
    @Valid
    @NotNull
    private Set<NutritionDto> nutritions = new HashSet<>();
    @Size(max = 1024, message = "Notes should not exceed 1024 characters.")
    private String notes;
}
