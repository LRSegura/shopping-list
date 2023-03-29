package com.code2ever.shoppinglist.api.rest.category;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.JsonSimpleResponse;
import com.code2ever.shoppinglist.services.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WsCategory {

    private final CategoryService service;

    public WsCategory(CategoryService service) {
        this.service = service;
    }

    @PostMapping("category")
    public ResponseEntity<Object> saveCategory(@RequestBody JsonCategory jsonCategory) {
        try {
            service.save(jsonCategory);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (ApplicationBusinessException e) {
            JsonSimpleResponse response = new JsonSimpleResponse(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error saving the category");
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
