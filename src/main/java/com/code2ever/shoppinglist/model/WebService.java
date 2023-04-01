package com.code2ever.shoppinglist.model;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.JsonData;
import com.code2ever.shoppinglist.api.rest.JsonDataResponse;
import com.code2ever.shoppinglist.api.rest.JsonResponse;
import com.code2ever.shoppinglist.api.rest.JsonSimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WebService {

    WsOperations getWsOperations();

    default ResponseEntity<Object> save(JsonData jsonResponse) {
        try {
            getWsOperations().save(jsonResponse);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ApplicationBusinessException e) {
            JsonSimpleResponse response = new JsonSimpleResponse(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error saving the category");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    default ResponseEntity<JsonResponse> getEntities() {
        try {
            List<? extends JsonData> entities = getWsOperations().getEntities();
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error getting categories");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    default ResponseEntity<JsonResponse> delete(Long id) {
        try {
            getWsOperations().delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error deleting category");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    default ResponseEntity<JsonResponse> update(JsonData json) {
        try {
            getWsOperations().update(json);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error deleting category");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
