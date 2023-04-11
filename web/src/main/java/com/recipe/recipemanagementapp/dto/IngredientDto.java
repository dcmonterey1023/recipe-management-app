package com.recipe.recipemanagementapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipe.recipemanagementapp.constants.MessageConstants;
import com.recipe.recipemanagementapp.entity.Recipe;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
public class IngredientDto {
    private long id;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String name;
    @DecimalMin(value = "0.01")
    private double amount;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String unitOfMeasure;
}
