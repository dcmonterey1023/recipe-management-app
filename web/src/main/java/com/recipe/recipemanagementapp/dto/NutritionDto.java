package com.recipe.recipemanagementapp.dto;

import com.recipe.recipemanagementapp.constants.MessageConstants;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NutritionDto {
    private long id;
    @Size(max = 50, message = MessageConstants.NOT_BLANK_MESSAGE)
    private String name;
    @DecimalMin("0.000001")
    @DecimalMax("100.00")
    private double percent;
}
