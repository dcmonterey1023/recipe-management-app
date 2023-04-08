package com.recipe.recipemanagementapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity(name = "INGREDIENT_TBL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Name should not be null/blank/empty.")
    private String name;
    @Min(value = 1, message = "Count should not be less than 1.")
    private int count;
    @NotBlank(message = "Unit of Measure should not be null/blank/empty.")
    private String unitOfMeasure;
    @Size(max = 255, message = "Description should not exceed 255 characters.")
    private String description;
    @ManyToOne
    @JsonIgnore
    private Recipe recipe;
}
