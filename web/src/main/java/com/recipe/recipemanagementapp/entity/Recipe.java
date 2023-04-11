package com.recipe.recipemanagementapp.entity;

import com.recipe.recipemanagementapp.constants.Categories;
import com.recipe.recipemanagementapp.constants.Cuisines;
import com.recipe.recipemanagementapp.constants.MessageConstants;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(indexes = @Index(columnList = "category"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Instruction> instructions = new HashSet<>();
    @Enumerated(value = EnumType.STRING)
    private Categories category;
    @Enumerated(value = EnumType.STRING)
    private Cuisines cuisine;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();
    private int serving;
    private int prepTime;
    @Enumerated(value = EnumType.STRING)
    private ChronoUnit prepTimeUnit;
    private int cookTime;
    @Enumerated(value = EnumType.STRING)
    private ChronoUnit cookTimeUnit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Nutrition> nutritions = new HashSet<>();
    @Column(length = 1024)
    private String notes;
}
