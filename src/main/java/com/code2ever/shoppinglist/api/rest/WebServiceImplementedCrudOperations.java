package com.code2ever.shoppinglist.api.rest;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WebServiceImplementedCrudOperations {

    WebServiceCrudOperations getWsOperations();

    default ResponseEntity<Object> save(JsonData jsonResponse) {
        try {
            getWsOperations().save(jsonResponse);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ApplicationBusinessException e) {
            JsonSimpleResponse response = new JsonSimpleResponse(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error saving the entity");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    default ResponseEntity<JsonResponse> get() {
        try {
            List<? extends JsonData> entities = getWsOperations().get();
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error getting entities");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    default ResponseEntity<JsonResponse> delete(Long id) {
        try {
            getWsOperations().delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error deleting entity");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    default ResponseEntity<JsonResponse> update(JsonData json) {
        try {
            getWsOperations().update(json);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error updating entity");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
