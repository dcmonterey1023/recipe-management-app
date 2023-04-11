package com.recipe.recipemanagementapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipe.recipemanagementapp.constants.MessageConstants;
import com.recipe.recipemanagementapp.constants.UnitOfMeasure;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(indexes = @Index(columnList = "name"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double amount;
    @Enumerated(value = EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;
    @ManyToOne(cascade = CascadeType.ALL)
    private Recipe recipe;
}
