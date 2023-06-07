package com.code2ever.shoppinglist.api.rest.detail;

import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.CrudRestOperations;
import com.code2ever.shoppinglist.api.rest.model.CrudWebService;
import com.code2ever.shoppinglist.api.rest.model.response.JsonDataResponse;
import com.code2ever.shoppinglist.api.rest.model.response.JsonResponse;
import com.code2ever.shoppinglist.api.rest.model.response.JsonSimpleResponse;
import com.code2ever.shoppinglist.services.detail.DetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.ws.rs.QueryParam;
import java.util.List;

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
        try {
            List<? extends JsonData> entities = service.restGet(idList);
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error getting entities");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/buy")
    public ResponseEntity<JsonResponse> getDetailToBuy(@QueryParam(value = "idList") Long idList) {
        try {
            List<? extends JsonData> entities = service.getDetailToBuy(idList);
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error getting entities");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/bought")
    public ResponseEntity<JsonResponse> getDetailBought(@QueryParam(value = "idList") Long idList) {
        try {
            List<? extends JsonData> entities = service.getDetailBought(idList);
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error getting entities");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/to/buy")
    public ResponseEntity<JsonResponse> buyDetail(@QueryParam(value = "idDetail") Long idDetail) {
        try {
            service.buyDetail(idDetail);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error checking detail as bought");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/cancel/buy")
    public ResponseEntity<JsonResponse> cancelBuyDetail(@QueryParam(value = "idDetail") Long idDetail) {
        try {
            service.cancelBuyDetail(idDetail);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            JsonSimpleResponse response = new JsonSimpleResponse("Error checking detail as not bought");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Override
    public CrudRestOperations<JsonDetail> getCrudRestOperations() {
        return service;
    }
}
