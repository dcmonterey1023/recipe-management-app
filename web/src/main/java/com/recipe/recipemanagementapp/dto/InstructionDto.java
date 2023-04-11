package com.recipe.recipemanagementapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipe.recipemanagementapp.entity.Recipe;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
public class InstructionDto {
    private long id;
    @Min(1)
    private int stepOrder;
    @Size(max = 1024)
    private String instruction;

}
