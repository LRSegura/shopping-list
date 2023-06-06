package com.code2ever.shoppinglist.services.item;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.CrudRestOperations;
import com.code2ever.shoppinglist.api.rest.item.JsonItem;
import com.code2ever.shoppinglist.api.util.UtilClass;
import com.code2ever.shoppinglist.model.category.Category;
import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.model.list.ShoppingList;
import com.code2ever.shoppinglist.repository.category.CategoryRepository;
import com.code2ever.shoppinglist.repository.item.ItemRepository;
import com.code2ever.shoppinglist.repository.list.ListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ItemService implements CrudRestOperations<JsonItem> {

    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    private final ListRepository listRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository, ListRepository listRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.listRepository = listRepository;
    }

    @Override
    public List<? extends JsonData> restGet() {
        return mapItemToJson(itemRepository.findAll());
    }
    @Override
    public void restSave(JsonItem json) {
        Objects.requireNonNull(json.name(),"Name cant be null");
        UtilClass.requireNonBlankString(json.name());
        Objects.requireNonNull(json.price(),"Price cant be null");
        Objects.requireNonNull(json.idCategory(), "IdCategory cant be null");
        if (isDuplicatedDuplicated(json.name())) {
            throw new ApplicationBusinessException("Item duplicated");
        }
        Item item = new Item();
        item.setName(json.name());
        item.setPrice(json.price());
        item.setCategory(getCategoryById(json.idCategory()));
        itemRepository.save(item);
    }

    @Override
    public void restUpdate(JsonItem jsonResponse) {
        Objects.requireNonNull(jsonResponse.id(),"Id cant be null");
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
        Objects.requireNonNull(id,"Id cant be null");
        itemRepository.deleteById(id);
    }

    public boolean isDuplicatedDuplicated(String name) {
        return itemRepository.existsByName(name);
    }

    public List<Category> getItemCategoryList() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException("Dont exist category with id " + id));
    }

    public List<JsonItem> getItemNotInList(Long id) {
        Objects.requireNonNull(id,"Id cant be null");
        String messageError = "Dont exist shopping list with id " + id;
        ShoppingList shoppingList =
                listRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException(messageError));
        List<Item> itemList = itemRepository.getItemNotInShoppingList(shoppingList);
        return mapItemToJson(itemList);
    }

    private List<JsonItem> mapItemToJson(List<Item> itemList){
        return itemList.stream().map(item -> new JsonItem(item.getId(), item.getName(), item.getPrice(),
                item.getCategory().getId())).toList();
    }
}