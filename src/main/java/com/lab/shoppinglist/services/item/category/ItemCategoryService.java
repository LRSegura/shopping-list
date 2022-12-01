package com.lab.shoppinglist.services.item.category;

import com.lab.shoppinglist.model.ItemCategory;
import com.lab.shoppinglist.repository.ItemCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository repository;

    public ItemCategoryService(ItemCategoryRepository repository) {
        this.repository = repository;
    }

    public void save(String categoryName){
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setDescription(categoryName);
        repository.save(itemCategory);
    }

    public boolean isItemCategoryDuplicated(String description){
        return repository.findItemCategoriesByDescription(description).isPresent();
    }
    public List<ItemCategory> getItemCategories(){
        return repository.findAll();
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
