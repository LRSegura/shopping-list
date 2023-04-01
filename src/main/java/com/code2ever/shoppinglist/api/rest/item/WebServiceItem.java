package com.code2ever.shoppinglist.api.rest.item;

import com.code2ever.shoppinglist.api.rest.JsonResponse;
import com.code2ever.shoppinglist.api.rest.WebService;
import com.code2ever.shoppinglist.api.rest.WebServiceOperations;
import com.code2ever.shoppinglist.services.item.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/api/item")
@Slf4j
public class WebServiceItem implements WebService {

    private final ItemService service;

    public WebServiceItem(ItemService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Object> saveCategory(@RequestBody JsonAddItem jsonAddCategory) {
        return save(jsonAddCategory);
    }
    @GetMapping()
    public ResponseEntity<JsonResponse> getCategory() {
        return getEntities();
    }

    @DeleteMapping()
    public ResponseEntity<JsonResponse> deleteCategory(@QueryParam(value = "id") Long id) {
        return delete(id);
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateCategory(@RequestBody JsonUpdateItem json) {
        return update(json);
    }

    @Override
    public WebServiceOperations getWsOperations() {
        return service;
    }
}
