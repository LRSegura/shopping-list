package com.code2ever.shoppinglist.api.rest.item;

import com.code2ever.shoppinglist.api.rest.model.WebService;
import com.code2ever.shoppinglist.api.rest.model.RestCrudOperations;
import com.code2ever.shoppinglist.services.item.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
@Slf4j
public class WebServiceItem extends WebService<JsonItem> {
    private final ItemService service;

    public WebServiceItem(ItemService service) {
        this.service = service;
    }

    @Override
    public RestCrudOperations<JsonItem> getRestCrudOperations() {
        return service;
    }
}
