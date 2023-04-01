package com.code2ever.shoppinglist.api.rest.category;

import com.code2ever.shoppinglist.api.rest.WebService;
import com.code2ever.shoppinglist.api.rest.WebServiceCrudOperations;
import com.code2ever.shoppinglist.services.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class WebServiceCategory extends WebService {

    private final CategoryService service;

    public WebServiceCategory(CategoryService service) {
        this.service = service;
    }

    @Override
    public WebServiceCrudOperations getWsOperations() {
        return service;
    }
}
