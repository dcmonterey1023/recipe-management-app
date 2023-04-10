package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.entity.Instruction;
import com.recipe.recipemanagementapp.entity.Recipe;
import com.recipe.recipemanagementapp.testdatafactory.RecipeTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InstructionServiceImplTest {

    InstructionService instructionService;
    @BeforeEach
    void setUp() {
        instructionService = new InstructionServiceImpl();
    }
    @Test
    void mapInstructionToRecipe() {
        //given
        Recipe recipe = RecipeTestDataFactory.createRecipeTestData();
        //when
        Set<Instruction> instructions = instructionService.mapInstructionToRecipe(recipe);
        //then
        assertEquals(recipe.getInstructions().size(), instructions.size());
    }
}