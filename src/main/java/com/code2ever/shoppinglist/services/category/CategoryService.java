package com.code2ever.shoppinglist.services.category;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.model.RestCrudOperations;
import com.code2ever.shoppinglist.api.rest.category.JsonCategory;
import com.code2ever.shoppinglist.api.util.UtilClass;
import com.code2ever.shoppinglist.model.category.Category;
import com.code2ever.shoppinglist.repository.category.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CategoryService implements RestCrudOperations<JsonCategory> {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<JsonCategory> restGet() {
        return repository.findAll().stream().map(category -> new JsonCategory(category.getId(), category.getDescription())).toList();
    }

    @Override
    public void restSave(JsonCategory json) {
        Objects.requireNonNull(json.description());
        UtilClass.requireNonBlankString(json.description());
        if (isDuplicatedCategory(json.description())) {
            throw new ApplicationBusinessException("Category name duplicated");
        }
        Category category = new Category();
        category.setDescription(json.description());
        repository.save(category);
    }

    public boolean isDuplicatedCategory(String description) {
        return repository.existsCategoryByDescription(description);
    }

    @Override
    public void restUpdate(JsonCategory jsonResponse) {
        Objects.requireNonNull(jsonResponse.id());
        Long id = jsonResponse.id();
        Category category = repository.findById(jsonResponse.id()).orElseThrow(() -> {
            String errorMessage = "Category not found with id" + id;
            return new ApplicationBusinessException(errorMessage);
        });
        if (Objects.nonNull(jsonResponse.description())) {
            category.setDescription(jsonResponse.description());
        }
        repository.save(category);
    }

    @Override
    public void restDelete(Long id) {
        Objects.requireNonNull(id);
        repository.deleteById(id);
    }
}