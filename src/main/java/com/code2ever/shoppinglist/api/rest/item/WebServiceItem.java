package com.code2ever.shoppinglist.api.rest.item;

import com.code2ever.shoppinglist.api.rest.model.CrudRestOperations;
import com.code2ever.shoppinglist.api.rest.model.CrudWebService;
import com.code2ever.shoppinglist.api.rest.model.JsonData;
import com.code2ever.shoppinglist.api.rest.model.response.JsonDataResponse;
import com.code2ever.shoppinglist.api.rest.model.response.JsonResponse;
import com.code2ever.shoppinglist.services.item.ItemService;
import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/item")
@Slf4j
public class WebServiceItem extends CrudWebService<JsonItem> {
    private final ItemService service;

    public WebServiceItem(ItemService service) {
        this.service = service;
    }

    @GetMapping("/not/in/list")
    public ResponseEntity<JsonResponse> getItemNotInList(@QueryParam(value = "idList") Long idList) {
        Supplier<ResponseEntity<JsonResponse>> supplierTry = () -> {
            List<? extends JsonData> entities = service.getItemNotInList(idList);
            JsonDataResponse jsonDataResponse = new JsonDataResponse(entities);
            return ResponseEntity.ok(jsonDataResponse);
        };
        return executeTryCatch(supplierTry, "Error getting entities");
    }

    @Override
    public CrudRestOperations<JsonItem> getCrudRestOperations() {
        return service;
    }
}
