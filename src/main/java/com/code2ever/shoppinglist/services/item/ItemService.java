package com.code2ever.shoppinglist.services.item;

import com.code2ever.shoppinglist.model.item.Item;
import com.code2ever.shoppinglist.model.item.Category;
import com.code2ever.shoppinglist.repository.item.ItemRepository;
import com.code2ever.shoppinglist.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

//    public void save(ItemForm itemForm){
//        Item item = new Item();
//        item.setName(itemForm.getName());
//        item.setPrice(itemForm.getPrice());
//        item.setItemCategory(getItemCategoryById(itemForm.getItemCategory()));
//        itemRepository.save(item);
//    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    public boolean isItemDuplicated(String name) {
        return itemRepository.findItemByName(name).isPresent();
    }

    public List<Category> getItemCategoryList() {
        return categoryRepository.findAll();
    }

    public Category getItemCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Dont exist item category with id " + id));
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}