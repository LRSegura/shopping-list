package com.code2ever.shoppinglist.api.rest.model;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.model.response.JsonDataResponse;
import com.code2ever.shoppinglist.api.rest.model.response.JsonResponse;
import com.code2ever.shoppinglist.api.rest.model.response.JsonSimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.ws.rs.QueryParam;
import java.util.List;

@Slf4j
public abstract class WebService<T extends JsonData> {


    @GetMapping()
    public ResponseEntity<JsonResponse> get() {
        try {
            List<? extends JsonData> entities = getRestCrudOperations().restGet();
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        } catch (Exception e) {
            log.error(e.getClass().getName() +"::"+ e.getMessage());
            JsonSimpleResponse response = new JsonSimpleResponse("Error getting entities");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping()
    public ResponseEntity<Object> save(@RequestBody T json) {
        try {
            getRestCrudOperations().restSave(json);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ApplicationBusinessException e) {
            log.error(e.getClass().getName() +"::"+ e.getMessage());
            JsonSimpleResponse response = new JsonSimpleResponse(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        } catch (Exception e) {
            log.error(e.getClass().getName() +"::"+ e.getMessage());
            JsonSimpleResponse response = new JsonSimpleResponse("Error saving the entity");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> update(@RequestBody T json) {
        try {
            getRestCrudOperations().restUpdate(json);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            log.error(e.getClass().getName() +"::"+ e.getMessage());
            JsonSimpleResponse response = new JsonSimpleResponse("Error updating entity");
            return ResponseEntity.internalServerError().body(response);
        }
    }
    @DeleteMapping()
    public ResponseEntity<JsonResponse> delete(@QueryParam(value = "id") Long id) {
        try {
            getRestCrudOperations().restDelete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getClass().getName() +"::"+ e.getMessage());
            JsonSimpleResponse response = new JsonSimpleResponse("Error deleting entity");
            return ResponseEntity.internalServerError().body(response);
        }
    }
    abstract public RestCrudOperations<T> getRestCrudOperations();
}
