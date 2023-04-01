package com.code2ever.shoppinglist.services.item;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.JsonData;
import com.code2ever.shoppinglist.api.rest.WebServiceCrudOperations;
import com.code2ever.shoppinglist.api.rest.item.JsonAddItem;
import com.code2ever.shoppinglist.api.rest.item.JsonItem;
import com.code2ever.shoppinglist.model.item.Category;
import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.repository.category.CategoryRepository;
import com.code2ever.shoppinglist.repository.item.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService implements WebServiceCrudOperations {

    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public <T extends JsonData> void save(T jsonResponse) {
        JsonAddItem jsonAddItem = (JsonAddItem) jsonResponse;
        if (isItemDuplicated(jsonAddItem.name())) {
            throw new ApplicationBusinessException("Item duplicated");
        }
        Item item = new Item();
        item.setName(jsonAddItem.name());
        item.setPrice(jsonAddItem.price());
        item.setCategory(getCategoryById(jsonAddItem.idCategory()));
        itemRepository.save(item);
    }

    @Override
    public List<? extends JsonData> get() {
        return itemRepository.findAll().stream().map(item -> new JsonItem(item.getId(), item.getName(), item.getPrice(),
                item.getCategory().getId())).toList();
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public <T extends JsonData> void update(T jsonResponse) {

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