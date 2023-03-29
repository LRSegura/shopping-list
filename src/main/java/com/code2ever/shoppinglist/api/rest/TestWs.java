package com.code2ever.shoppinglist.api.rest;

import com.code2ever.shoppinglist.model.item.Category;
import com.code2ever.shoppinglist.repository.category.CategoryRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestWs {

    private final CategoryRepository repository;

    public TestWs(CategoryRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/json")
    public List<Category> getItemCategories(){
        return repository.findAll();
    }

}