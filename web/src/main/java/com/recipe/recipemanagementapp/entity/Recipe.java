package com.recipe.recipemanagementapp.entity;

import com.recipe.recipemanagementapp.constants.MessageConstants;
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
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    @Column(updatable = false)
    private String name;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    @Size(max = 150)
    private String description;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    @Size(max = 1024)
    private String instruction;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String category;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String cuisine;
    @Size(min = 1, message = "Ingredient must have at least one entry.")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();
    @Min(value = 1, message = "Serving should not be less than 1.")
    private int serving;
    @Min(value = 1, message = "Preparation time should not be less than 1.")
    private int prep_time;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String prep_time_unit;
    @Min(value = 1, message = "Cooking time should not be less than 1.")
    private int cook_time;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String cook_time_unit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Nutrition> nutritions = new HashSet<>();
    @Size(max = 255, message = "Notes should not exceed 255 characters.")
    private String notes;
}
