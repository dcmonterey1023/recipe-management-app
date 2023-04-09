package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.constants.UnitOfMeasure;
import com.recipe.recipemanagementapp.exception.UnitOfMeasureNotValidException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    @Override
    public void validateUnitOfMeasure(String unitOfMeasure) {
        Arrays.stream(UnitOfMeasure.values())
                .filter(unit -> unit.name().equalsIgnoreCase(unitOfMeasure))
                .findAny()
                .orElseThrow(
                        () -> new UnitOfMeasureNotValidException(
                                String.format("Unit of Measure %s not valid.", unitOfMeasure)
                        )
                );
    }
}
