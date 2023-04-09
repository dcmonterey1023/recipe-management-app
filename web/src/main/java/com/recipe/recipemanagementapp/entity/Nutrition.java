package com.recipe.recipemanagementapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipe.recipemanagementapp.constants.MessageConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity(name = "NUTRITION_TBL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
public class Nutrition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(max = 50, message = MessageConstants.NOT_BLANK_MESSAGE)
    private String name;
    @DecimalMin("0.000001")
    @DecimalMax("0.99")
    private double percent;
    @ManyToOne
    @JsonIgnore
    private Recipe recipe;

}
