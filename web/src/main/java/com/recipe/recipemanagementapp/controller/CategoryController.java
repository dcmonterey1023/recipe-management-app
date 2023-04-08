package com.recipe.recipemanagementapp.controller;

import com.recipe.recipemanagementapp.dto.CategoryResponse;
import com.recipe.recipemanagementapp.entity.Category;
import com.recipe.recipemanagementapp.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService recipeCategoryService){
        this.categoryService = recipeCategoryService;
    }
    @GetMapping
    public ResponseEntity<CategoryResponse> getAllRecipeCategory(){
        //TODO implement further checking
        return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        categoryService.addCategory(category);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<String> addCategories(@RequestBody @Valid List<Category> categories){
        categoryService.addMultipleCategory(categories);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }

    @DeleteMapping("/**")
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, code = HttpStatus.NOT_IMPLEMENTED)
    public void deleteCategory(){
    }

    @PatchMapping("/**")
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, code = HttpStatus.NOT_IMPLEMENTED)
    public void updateCategory(){
    }

}
