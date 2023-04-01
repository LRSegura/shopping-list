package com.code2ever.shoppinglist.api.rest;

import com.code2ever.shoppinglist.api.rest.item.JsonAddItem;
import com.code2ever.shoppinglist.api.rest.item.JsonUpdateItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

public abstract class WebService implements WebServiceImplementedCrudOperations {
    @PostMapping()
    public ResponseEntity<Object> saveEntity(@RequestBody JsonAddItem jsonAddCategory) {
        return save(jsonAddCategory);
    }
    @GetMapping()
    public ResponseEntity<JsonResponse> getEntities() {
        return get();
    }

    @DeleteMapping()
    public ResponseEntity<JsonResponse> deleteEntity(@QueryParam(value = "id") Long id) {
        return delete(id);
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateEntity(@RequestBody JsonUpdateItem json) {
        return update(json);
    }
}
