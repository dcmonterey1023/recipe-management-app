package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.exception.UnitOfMeasureNotValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;
    @BeforeEach
    void setUp() {
        unitOfMeasureService = new UnitOfMeasureServiceImpl();
    }
    @Test
    void validateUnitOfMeasureFail() {
        assertThrows(UnitOfMeasureNotValidException.class, () -> unitOfMeasureService.validateUnitOfMeasure("invalid"));
    }
}