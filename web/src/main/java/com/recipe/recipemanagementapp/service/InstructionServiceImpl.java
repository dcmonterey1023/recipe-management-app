package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.InstructionDto;
import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.entity.Instruction;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.mapper.InstructionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InstructionServiceImpl implements InstructionService {
    private final InstructionMapper mapper;
    public InstructionServiceImpl(InstructionMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public Set<Instruction> mapInstructionToRecipe(RecipeDto recipeDto, Recipe recipe) {
        return recipeDto.getInstructions()
                .stream()
                .map(instruction -> {
                    Instruction mappedInstruction = mapper.mapInstructionDtoToInstruction(instruction);
                    mappedInstruction.setRecipe(recipe);
                    return mappedInstruction;
                }).collect(Collectors.toSet());
    }
}
