package com.recipe.recipemanagementapp.dto;

import com.recipe.recipemanagementapp.constants.MessageConstants;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {
    private long id;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String name;
    @DecimalMin(value = "0.01")
    private double amount;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String unitOfMeasure;
}
