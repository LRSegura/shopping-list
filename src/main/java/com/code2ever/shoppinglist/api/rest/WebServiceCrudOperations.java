package com.code2ever.shoppinglist.api.rest;

import java.util.List;

public interface WebServiceCrudOperations {

    <T extends JsonData> void save(T jsonResponse);

    List<? extends JsonData> get();

    void delete(Long id);

    <T extends JsonData> void update(T jsonResponse);
}
