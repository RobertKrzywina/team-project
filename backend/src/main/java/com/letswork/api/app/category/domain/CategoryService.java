package com.letswork.api.app.category.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
class CategoryService {

    private final CategoryRepository repository;
    private final CategoryValidator validator;

    public CategoryEntity findByCategoryName(String categoryName) {
        validator.validateCategoryName(categoryName);
        return repository.findByName(categoryName);
    }

    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }
}
