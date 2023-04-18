package com.code2ever.shoppinglist.api.rest.list;

import com.code2ever.shoppinglist.api.rest.model.RestCrudOperations;
import com.code2ever.shoppinglist.api.rest.model.WebService;
import com.code2ever.shoppinglist.services.list.ListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/list")
@Slf4j
public class WebServiceList extends WebService<JsonList> {
    private final ListService service;

    public WebServiceList(ListService service) {
        this.service = service;
    }

    @Override
    public RestCrudOperations<JsonList> getRestCrudOperations() {
        return service;
    }
}
