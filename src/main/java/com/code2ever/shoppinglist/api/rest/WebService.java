package com.code2ever.shoppinglist.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

public abstract class WebService<T extends JsonData, K extends JsonData> implements WebServiceImplementedCrudOperations {
    @PostMapping()
    public ResponseEntity<Object> saveEntity(@RequestBody T jsonAdd) {
        return save(jsonAdd);
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
    public ResponseEntity<JsonResponse> updateEntity(@RequestBody K jsonUpdate) {
        return update(jsonUpdate);
    }
}
