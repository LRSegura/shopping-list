package com.code2ever.shoppinglist.api.rest.model;

import com.code2ever.shoppinglist.api.exceptions.ApplicationBusinessException;
import com.code2ever.shoppinglist.api.rest.model.response.JsonDataResponse;
import com.code2ever.shoppinglist.api.rest.model.response.JsonResponse;
import com.code2ever.shoppinglist.api.rest.model.response.JsonSimpleResponse;
import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public abstract class CrudWebService<T extends JsonData> {


    @GetMapping()
    public ResponseEntity<JsonResponse> get() {
        Supplier<ResponseEntity<JsonResponse>> supplier = () -> {
            List<? extends JsonData> entities = getCrudRestOperations().restGet();
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        };

        Function<Exception, ResponseEntity<JsonResponse>> function = exception -> executeException(exception, "Error getting entities");
        return execute(supplier, function);
    }

    @PostMapping()
    public ResponseEntity<JsonResponse> save(@RequestBody T json) {

        Supplier<ResponseEntity<JsonResponse>> supplier = () -> {
            getCrudRestOperations().restSave(json);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        };

        Function<Exception, ResponseEntity<JsonResponse>> function = exception -> executeException(exception, "Error saving the entity");
        return execute(supplier, function);

    }

    @PutMapping()
    public ResponseEntity<JsonResponse> update(@RequestBody T json) {
        Supplier<ResponseEntity<JsonResponse>> supplier = () -> {
            getCrudRestOperations().restUpdate(json);
            return ResponseEntity.accepted().build();
        };
        Function<Exception, ResponseEntity<JsonResponse>> function = exception -> executeException(exception, "Error updating entity");
        return execute(supplier, function);
    }

    @DeleteMapping()
    public ResponseEntity<JsonResponse> delete(@QueryParam(value = "id") Long id) {
        Supplier<ResponseEntity<JsonResponse>> supplier = () -> {
            getCrudRestOperations().restDelete(id);
            return ResponseEntity.noContent().build();
        };
        Function<Exception, ResponseEntity<JsonResponse>> function = exception -> executeException(exception, "Error deleting entity");
        return execute(supplier, function);
    }

    private ResponseEntity<JsonResponse> execute(Supplier<ResponseEntity<JsonResponse>> supplier, Function<Exception, ResponseEntity<JsonResponse>> function) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            return function.apply(exception);
        }
    }

    private ResponseEntity<JsonResponse> executeException(Exception exception, String message) {
        log.error(exception.getClass().getName() + "::" + exception.getMessage());
        String responseMessage = exception instanceof ApplicationBusinessException ? exception.getMessage() : message;
        JsonSimpleResponse response = new JsonSimpleResponse(responseMessage);
        return ResponseEntity.internalServerError().body(response);
    }

    abstract public CrudRestOperations<T> getCrudRestOperations();
}
