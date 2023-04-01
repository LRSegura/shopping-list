package com.code2ever.shoppinglist.services.category;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.JsonData;
import com.code2ever.shoppinglist.api.rest.category.JsonAddCategory;
import com.code2ever.shoppinglist.api.rest.category.JsonCategory;
import com.code2ever.shoppinglist.api.rest.category.JsonUpdateCategory;
import com.code2ever.shoppinglist.api.rest.WebServiceOperations;
import com.code2ever.shoppinglist.model.item.Category;
import com.code2ever.shoppinglist.repository.category.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService implements WebServiceOperations {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public boolean isCategoryDuplicated(String description) {
        return repository.findItemCategoriesByDescription(description).isPresent();
    }

    @Override
    public <T extends JsonData> void save(T jsonResponse) {
        JsonAddCategory jsonAddCategory = (JsonAddCategory) jsonResponse;
        if (isCategoryDuplicated(jsonAddCategory.description())) {
            throw new ApplicationBusinessException("Category name duplicated");
        }
        Category category = new Category();
        category.setDescription(jsonAddCategory.description());
        repository.save(category);
    }

    @Override
    public List<JsonCategory> getEntities() {
        return repository.findAll().stream().map(category -> new JsonCategory(category.getId(), category.getDescription())).toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public <T extends JsonData> void update(T jsonResponse) {
        JsonUpdateCategory jsonUpdateCategory = (JsonUpdateCategory) jsonResponse;
        Long id = jsonUpdateCategory.id();
        String errorMessage = "Entity not found with id"+ id;
        Category category =
                repository.findById(jsonUpdateCategory.id()).orElseThrow(() -> new ApplicationBusinessException(errorMessage));
        if(jsonUpdateCategory.description() != null){
            category.setDescription(jsonUpdateCategory.description());
        }
        repository.save(category);
    }
}