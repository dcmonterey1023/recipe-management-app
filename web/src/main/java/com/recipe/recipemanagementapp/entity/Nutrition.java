package com.recipe.recipemanagementapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipe.recipemanagementapp.constants.MessageConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
public class Nutrition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double percent;
    @ManyToOne(cascade = CascadeType.ALL)
    private Recipe recipe;

}
