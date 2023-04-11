package com.recipe.recipemanagementapp.entity;

import com.recipe.recipemanagementapp.constants.Categories;
import com.recipe.recipemanagementapp.constants.Cuisines;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
