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
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            List<? extends JsonData> entities = getCrudRestOperations().restGet();
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        };
        return executeTryCatch(supplierTry, "Error getting entities");
    }

    @PostMapping()
    public ResponseEntity<JsonResponse> save(@RequestBody T json) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            getCrudRestOperations().restSave(json);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        };
        return executeTryCatch(supplierTry, "Error saving entity");
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> update(@RequestBody T json) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            getCrudRestOperations().restUpdate(json);
            return ResponseEntity.accepted().build();
        };
        return executeTryCatch(supplierTry, "Error updating entity");
    }

    @DeleteMapping()
    public ResponseEntity<JsonResponse> delete(@QueryParam(value = "id") Long id) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            getCrudRestOperations().restDelete(id);
            return ResponseEntity.noContent().build();
        };
        return executeTryCatch(supplierTry, "Error deleting entity");
    }

    protected ResponseEntity<JsonResponse> executeTryCatch(Supplier<ResponseEntity<JsonResponse>> supplier, String exceptionMessage) {
        Function<Exception, ResponseEntity<JsonResponse>> functionCatch = exception -> executeCatchException(exception, exceptionMessage);
        return executeTryCatch(supplier, functionCatch);
    }

    protected ResponseEntity<JsonResponse> executeTryCatch(Supplier<ResponseEntity<JsonResponse>> supplier,
                                                           Function<Exception, ResponseEntity<JsonResponse>> function) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            return function.apply(exception);
        }
    }
    private ResponseEntity<JsonResponse> executeCatchException(Exception exception, String customMessage) {
        String errorMessage = exception.getClass().getName() + "::" + exception.getMessage();
        log.error(errorMessage);
        String responseMessage = exception instanceof ApplicationBusinessException ? exception.getMessage() : customMessage;
        JsonSimpleResponse response = new JsonSimpleResponse(responseMessage);
        return ResponseEntity.internalServerError().body(response);
    }

    abstract public CrudRestOperations<T> getCrudRestOperations();
}
