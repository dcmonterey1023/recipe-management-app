package com.recipe.recipemanagementapp.service;

import com.recipe.recipemanagementapp.dto.CategoryResponse;
import com.recipe.recipemanagementapp.exception.CategoryAlreadyExistException;
import com.recipe.recipemanagementapp.exception.InvalidRecipeCategoryException;
import com.recipe.recipemanagementapp.entity.Category;
import com.recipe.recipemanagementapp.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.recipe.recipemanagementapp.constants.ErrorMessageConstants.CATEGORY_ALREADY_EXIST;
import static com.recipe.recipemanagementapp.constants.ErrorMessageConstants.CATEGORY_NOT_FOUND;
import static com.recipe.recipemanagementapp.constants.MessageConstants.VALIDATE_CATEGORY;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse getAllCategory() {
        var categoryResponse = new CategoryResponse();
        categoryRepository.findAll()
                .forEach(category -> categoryResponse.getCategories().add(category));
        categoryResponse.setCount(categoryResponse.getCategories().size());
        return categoryResponse;
    }

    @Override
    public void addCategory(Category category) {
        validateAddCategory(category);
        categoryRepository.save(category);
    }

    @Override
    public void addMultipleCategory(List<Category> categories) {
        categories.forEach(this::validateAddCategory);
        categoryRepository.saveAll(categories);
    }

    @Override
    public void validateCategory(String categoryName) {
        //TODO use Predicate negate
        log.info(VALIDATE_CATEGORY, categoryName);
        if(categoryRepository.findByName(categoryName).isEmpty()){
            throw new InvalidRecipeCategoryException(
                    String.format(CATEGORY_NOT_FOUND, categoryName));
        }
    }

    private void validateAddCategory(Category category){
        //TODO use Predicate negate
        log.info(VALIDATE_CATEGORY, category.getName());
        var optionalCategory = categoryRepository.findByName(category.getName());
        if(optionalCategory.isPresent()){
            throw new CategoryAlreadyExistException(
                    String.format(CATEGORY_ALREADY_EXIST, category.getName())
            );
        }
    }
}
