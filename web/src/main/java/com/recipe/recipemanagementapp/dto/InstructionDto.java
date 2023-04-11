package com.recipe.recipemanagementapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionDto {
    private long id;
    @Min(1)
    private int stepOrder;
    @Size(max = 1024)
    private String instruction;

}
