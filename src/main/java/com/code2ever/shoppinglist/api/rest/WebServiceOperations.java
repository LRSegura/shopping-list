package com.code2ever.shoppinglist.api.rest;

import java.util.List;

public interface WebServiceOperations {

    <T extends JsonData> void save(T jsonResponse);

    List<? extends JsonData> getEntities();

    void delete(Long id);

    <T extends JsonData> void update(T jsonResponse);
}
