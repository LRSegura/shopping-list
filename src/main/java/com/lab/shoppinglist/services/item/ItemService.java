package com.lab.shoppinglist.services.item;

import com.lab.shoppinglist.model.item.Item;
import com.lab.shoppinglist.model.item.ItemCategory;
import com.lab.shoppinglist.repository.item.ItemRepository;
import com.lab.shoppinglist.repository.item.category.ItemCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemService(ItemRepository itemRepository, ItemCategoryRepository itemCategoryRepository) {
        this.itemRepository = itemRepository;
        this.itemCategoryRepository = itemCategoryRepository;
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

    public List<ItemCategory> getItemCategoryList() {
        return itemCategoryRepository.findAll();
    }

    public ItemCategory getItemCategoryById(Long id) {
        return itemCategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Dont exist item category with id " + id));
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}