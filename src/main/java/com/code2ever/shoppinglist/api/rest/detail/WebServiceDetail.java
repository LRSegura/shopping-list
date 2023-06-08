package com.code2ever.shoppinglist.api.rest.detail;

import com.code2ever.shoppinglist.api.rest.model.CrudRestOperations;
import com.code2ever.shoppinglist.api.rest.model.CrudWebService;
import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.response.JsonDataResponse;
import com.code2ever.shoppinglist.api.rest.model.response.JsonResponse;
import com.code2ever.shoppinglist.services.detail.DetailService;
import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/detail")
@Slf4j
public class WebServiceDetail extends CrudWebService<JsonDetail> {
    private final DetailService service;

    public WebServiceDetail(DetailService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<JsonResponse> get(@QueryParam(value = "idList") Long idList) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            List<? extends JsonData> entities = service.restGet(idList);
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        };
        return executeTryCatch(supplierTry, "Error getting entities");
    }

    @GetMapping("/buy")
    public ResponseEntity<JsonResponse> getDetailToBuy(@QueryParam(value = "idList") Long idList) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            List<? extends JsonData> entities = service.getDetailToBuy(idList);
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        };
        return executeTryCatch(supplierTry, "Error getting entities");
    }

    @GetMapping("/bought")
    public ResponseEntity<JsonResponse> getDetailBought(@QueryParam(value = "idList") Long idList) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            List<? extends JsonData> entities = service.getDetailBought(idList);
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        };
        return executeTryCatch(supplierTry, "Error getting entities");
    }

    @PutMapping("/to/buy")
    public ResponseEntity<JsonResponse> buyDetail(@QueryParam(value = "idDetail") Long idDetail) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            service.buyDetail(idDetail);
            return ResponseEntity.accepted().build();
        };
        return executeTryCatch(supplierTry, "Error checking detail as bought");
    }

    @PutMapping("/cancel/buy")
    public ResponseEntity<JsonResponse> cancelBuyDetail(@QueryParam(value = "idDetail") Long idDetail) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            service.cancelBuyDetail(idDetail);
            return ResponseEntity.accepted().build();
        };
        return executeTryCatch(supplierTry, "Error checking detail as not bought");
    }

    @Override
    public CrudRestOperations<JsonDetail> getCrudRestOperations() {
        return service;
    }
}
