package com.code2ever.shoppinglist.api.rest.list;

import com.code2ever.shoppinglist.api.rest.model.CrudRestOperations;
import com.code2ever.shoppinglist.api.rest.model.CrudWebService;
import com.code2ever.shoppinglist.services.list.ListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/list")
@Slf4j
public class WebServiceList extends CrudWebService<JsonList> {
    private final ListService service;

    public WebServiceList(ListService service) {
        this.service = service;
    }

    @Override
    public CrudRestOperations<JsonList> getCrudRestOperations() {
        return service;
    }
}
