package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.InstructionDto;
import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.entity.Instruction;
import com.recipe.recipemanagementapp.entity.Recipe;

import java.util.Set;

public interface InstructionService {

    public Set<Instruction> mapInstructionToRecipe(RecipeDto recipeDto, Recipe recipe);

}
