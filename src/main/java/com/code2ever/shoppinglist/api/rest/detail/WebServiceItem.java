package com.code2ever.shoppinglist.api.rest.detail;

import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.RestCrudOperations;
import com.code2ever.shoppinglist.api.rest.model.WebService;
import com.code2ever.shoppinglist.api.rest.model.response.JsonDataResponse;
import com.code2ever.shoppinglist.api.rest.model.response.JsonResponse;
import com.code2ever.shoppinglist.api.rest.model.response.JsonSimpleResponse;
import com.code2ever.shoppinglist.services.detail.DetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/api/detail")
@Slf4j
public class WebServiceItem extends WebService<JsonDetail> {
    private final DetailService service;

    public WebServiceItem(DetailService service) {
        this.service = service;
    }

    @GetMapping()
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

    @Override
    public RestCrudOperations<JsonDetail> getRestCrudOperations() {
        return service;
    }
}
