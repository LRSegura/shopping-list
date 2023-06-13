package com.code2ever.shoppinglist.services.category;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.category.JsonCategory;
import com.code2ever.shoppinglist.api.rest.model.CrudRestOperations;
import com.code2ever.shoppinglist.api.util.UtilClass;
import com.code2ever.shoppinglist.model.category.Category;
import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.repository.category.CategoryRepository;
import com.code2ever.shoppinglist.repository.item.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CategoryService implements CrudRestOperations<JsonCategory> {

    private final CategoryRepository repository;

    private final ItemRepository itemRepository;

    public CategoryService(CategoryRepository repository, ItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<JsonCategory> restGet() {
        return repository.findAll().stream().map(category -> new JsonCategory(category.getId(), category.getDescription())).toList();
    }

    @Override
    public void restSave(JsonCategory json) {
        Objects.requireNonNull(json.description(), "Description cant be null");
        UtilClass.requireNonBlankString(json.description());
        if (isDuplicatedCategory(json.description())) {
            throw new ApplicationBusinessException("Duplicated category name");
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
        Objects.requireNonNull(jsonResponse.id(), "Id cant be null");
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
        Objects.requireNonNull(id, "Id cant be null");
        Category category = getCategoryById(id);
        List<Item> itemList = itemRepository.getItemsByCategory(category);
        if (!itemList.isEmpty()) {
            throw new ApplicationBusinessException("The category cant be deleted because there is items with this " + "category");
        }
        repository.deleteById(id);
    }

    private Category getCategoryById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ApplicationBusinessException("There is no category with the id" + id));
    }
}