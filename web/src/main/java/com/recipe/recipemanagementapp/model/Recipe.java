package com.recipe.recipemanagementapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "RECIPE_TBL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name should not be null/blank/empty.")
    @Column(updatable = false)
    private String name;

    @NotBlank(message = "Description should not be null/blank/empty.")
    @Size(max = 50)
    private String description;

    @NotBlank(message = "Instruction should not be null/blank/empty.")
    private String instruction;

    @NotBlank(message = "Category should not be null/blank/empty.")
    private String category;

    @NotBlank(message = "Cuisine should not be null/blank/empty.")
    private String cuisine;

    @Size(min = 1, message = "Ingredient must have at least one entry.")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Ingredient> ingredients;
    @Min(value = 1, message = "Serving should not be less than 1.")
    private int serving;

    @Min(value = 1, message = "Preparation time should not be less than 1.")
    private int preparation_time;

    @Size(max = 255, message = "Notes should not exceed 255 characters.")
    private String notes;
}
