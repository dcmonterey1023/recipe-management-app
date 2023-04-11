package com.recipe.recipemanagementapp.mapper;

import com.recipe.recipemanagementapp.dto.InstructionDto;
import com.recipe.recipemanagementapp.entity.Instruction;
import org.mapstruct.Mapper;

@Mapper
public interface InstructionMapper {

    Instruction mapInstructionDtoToInstruction(InstructionDto dto);

}
