package com.code2ever.shoppinglist.services.item;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.RestCrudOperations;
import com.code2ever.shoppinglist.api.rest.item.JsonItem;
import com.code2ever.shoppinglist.model.item.Category;
import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.repository.category.CategoryRepository;
import com.code2ever.shoppinglist.repository.item.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ItemService implements RestCrudOperations<JsonItem> {

    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<? extends JsonData> restGet() {
        return itemRepository.findAll().stream().map(item -> new JsonItem(item.getId(), item.getName(), item.getPrice(), item.getCategory().getId())).toList();
    }
    @Override
    public void restSave(JsonItem jsonResponse) {
        if (isItemDuplicated(jsonResponse.name())) {
            throw new ApplicationBusinessException("Item duplicated");
        }
        Item item = new Item();
        item.setName(jsonResponse.name());
        item.setPrice(jsonResponse.price());
        item.setCategory(getCategoryById(jsonResponse.idCategory()));
        itemRepository.save(item);
    }

    @Override
    public void restUpdate(JsonItem jsonResponse) {
        Objects.requireNonNull(jsonResponse.id());
        Long id = jsonResponse.id();
        Item item = itemRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "There isn't exist an item with id " + id;
            return new ApplicationBusinessException(errorMessage);
        });
        if (Objects.nonNull(jsonResponse.price())) {
            item.setPrice(jsonResponse.price());
        }
        if (Objects.nonNull(jsonResponse.name())) {
            item.setName(jsonResponse.name());
        }
        if (Objects.nonNull(jsonResponse.idCategory())) {
            item.setCategory(getCategoryById(jsonResponse.idCategory()));
        }
        itemRepository.save(item);
    }

    @Override
    public void restDelete(Long id) {
        itemRepository.deleteById(id);
    }

    public boolean isItemDuplicated(String name) {
        return itemRepository.findItemByName(name).isPresent();
    }

    public List<Category> getItemCategoryList() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException("Dont exist category with id " + id));
    }
}