package com.recipe.recipemanagementapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(indexes = @Index(columnList = "instruction"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int stepOrder;
    @Column(length = 1024)
    private String instruction;
    @ManyToOne(cascade = CascadeType.ALL)
    private Recipe recipe;

}
