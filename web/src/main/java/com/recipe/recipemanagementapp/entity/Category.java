package com.recipe.recipemanagementapp.entity;

import com.recipe.recipemanagementapp.constants.MessageConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = MessageConstants.NOT_BLANK_MESSAGE)
    private String name;

    @Size(max = 255, message = "Description should not exceed 255 characters.")
    private String description;
}
