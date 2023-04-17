package com.code2ever.shoppinglist.api.rest.category;

import com.code2ever.shoppinglist.api.rest.model.WebService;
import com.code2ever.shoppinglist.api.rest.model.RestCrudOperations;
import com.code2ever.shoppinglist.services.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class WebServiceCategory extends WebService<JsonCategory> {

    private final CategoryService service;

    public WebServiceCategory(CategoryService service) {
        this.service = service;
    }

    @Override
    public RestCrudOperations<JsonCategory> getWsOperations() {
        return service;
    }
}
