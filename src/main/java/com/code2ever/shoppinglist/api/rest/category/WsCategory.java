package com.code2ever.shoppinglist.api.rest.category;

import com.code2ever.shoppinglist.api.rest.JsonResponse;
import com.code2ever.shoppinglist.model.WebService;
import com.code2ever.shoppinglist.model.WsOperations;
import com.code2ever.shoppinglist.services.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/api")
@Slf4j
public class WsCategory implements WebService {

    private final CategoryService service;

    public WsCategory(CategoryService service) {
        this.service = service;
    }

    @PostMapping("category")
    public ResponseEntity<Object> saveCategory(@RequestBody JsonAddCategory jsonAddCategory) {
        return save(jsonAddCategory);
    }

    @GetMapping("category")
    public ResponseEntity<JsonResponse> getCategory() {
        return getEntities();
    }

    @DeleteMapping("category")
    public ResponseEntity<JsonResponse> deleteCategory(@QueryParam(value = "id") Long id) {
        return delete(id);
    }

    @PutMapping("category")
    public ResponseEntity<JsonResponse> updateCategory(@RequestBody JsonUpdateCategory json) {
        return update(json);
    }

    @Override
    public WsOperations getWsOperations() {
        return service;
    }
}
