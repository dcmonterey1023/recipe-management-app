package com.recipe.recipemanagementapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity(name = "CATEGORY_TBL")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name should not be null/blank/empty.")
    private String name;

    @Size(max = 255, message = "Description should not exceed 255 characters.")
    private String description;
}
