package com.lab.shoppinglist.webservices;

import com.lab.shoppinglist.model.ItemCategory;
import com.lab.shoppinglist.repository.ItemCategoryRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestWs {

    private final ItemCategoryRepository repository;

    public TestWs(ItemCategoryRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/json")
    public List<ItemCategory> getItemCategories(){
        return repository.findAll();
    }

}
