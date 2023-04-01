package com.code2ever.shoppinglist.model;

import com.code2ever.shoppinglist.api.rest.JsonData;
import com.code2ever.shoppinglist.api.rest.JsonResponse;

import java.util.List;

public interface WsOperations {

    <T extends JsonData> void save(T jsonResponse);

    List<? extends JsonData> getEntities();

    void delete(Long id);

    <T extends JsonData> void update(T jsonResponse);
}
