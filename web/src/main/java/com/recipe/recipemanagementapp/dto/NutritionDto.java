package com.recipe.recipemanagementapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipe.recipemanagementapp.constants.MessageConstants;
import com.recipe.recipemanagementapp.entity.Recipe;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
public class NutritionDto {
    private long id;
    @Size(max = 50, message = MessageConstants.NOT_BLANK_MESSAGE)
    private String name;
    @DecimalMin("0.000001")
    @DecimalMax("100.00")
    private double percent;
}
