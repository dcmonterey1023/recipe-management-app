package com.recipe.recipemanagementapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipe.recipemanagementapp.constants.MessageConstants;
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
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String name;
    @DecimalMin(value = "0.01")
    private double amount;
    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String unitOfMeasure;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Recipe recipe;
}
