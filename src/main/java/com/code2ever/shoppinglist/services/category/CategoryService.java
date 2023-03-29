package com.code2ever.shoppinglist.services.category;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.category.JsonCategory;
import com.code2ever.shoppinglist.model.item.Category;
import com.code2ever.shoppinglist.repository.category.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public void save(JsonCategory jsonCategory) {
        Category category = new Category();
        category.setDescription(jsonCategory.description());
        if(isCategoryDuplicated(jsonCategory.description())){
            throw new ApplicationBusinessException("Category name duplicated");
        }
        repository.save(category);
        log.info("saving " + category);
    }

    public boolean isCategoryDuplicated(String description){
        return repository.findItemCategoriesByDescription(description).isPresent();
    }
    public List<Category> getCategories(){
        return repository.findAll();
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}