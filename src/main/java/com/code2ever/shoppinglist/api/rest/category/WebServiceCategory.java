package com.code2ever.shoppinglist.api.rest.category;

import com.code2ever.shoppinglist.api.rest.JsonResponse;
import com.code2ever.shoppinglist.api.rest.WebService;
import com.code2ever.shoppinglist.api.rest.WebServiceOperations;
import com.code2ever.shoppinglist.services.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class WebServiceCategory implements WebService {

    private final CategoryService service;

    public WebServiceCategory(CategoryService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Object> saveCategory(@RequestBody JsonAddCategory jsonAddCategory) {
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
    public ResponseEntity<JsonResponse> updateCategory(@RequestBody JsonUpdateCategory json) {
        return update(json);
    }

    @Override
    public WebServiceOperations getWsOperations() {
        return service;
    }
}
