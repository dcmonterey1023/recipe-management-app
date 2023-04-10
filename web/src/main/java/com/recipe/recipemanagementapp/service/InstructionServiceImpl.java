package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.entity.Instruction;
import com.recipe.recipemanagementapp.entity.Recipe;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InstructionServiceImpl implements InstructionService {
    @Override
    public Set<Instruction> mapInstructionToRecipe(Recipe recipe) {
        return recipe.getInstructions()
                .stream()
                .map(instruction -> {
                    instruction.setRecipe(recipe);
                    return instruction;
                }).collect(Collectors.toSet());
    }
}
