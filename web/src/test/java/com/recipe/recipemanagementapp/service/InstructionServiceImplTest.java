package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.InstructionDto;
import com.recipe.recipemanagementapp.dto.RecipeDto;
import com.recipe.recipemanagementapp.entity.Instruction;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.mapper.InstructionMapper;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructionServiceImplTest {

    private InstructionService instructionService;
    @Mock
    InstructionMapper mapper;
    @BeforeEach
    void setUp() {
        instructionService = new InstructionServiceImpl(mapper);
    }
    @Test
    void mapInstructionToRecipe_successful() {
        //given
        RecipeDto recipeDto = RecipeTestDataFactory.createRecipeTestData();
        Recipe recipeEntity = RecipeTestDataFactory.createRecipeEntityResponseTestData();
        InstructionDto instructionDto = RecipeTestDataFactory.createSingleInstructionDtoTestData();
        Instruction instruction = RecipeTestDataFactory.createSingleInstructionEntityTestData();
        recipeDto.setInstructions(Set.of(instructionDto));
        recipeEntity.setInstructions(Set.of(instruction));
        //when
        when(mapper.mapInstructionDtoToInstruction(instructionDto)).thenReturn(instruction);
        Set<Instruction> instructions = instructionService.mapInstructionToRecipe(recipeDto,recipeEntity);
        //then
        assertEquals(recipeDto.getInstructions().size(), instructions.size());
        verify(mapper, times(1)).mapInstructionDtoToInstruction(instructionDto);
    }
}