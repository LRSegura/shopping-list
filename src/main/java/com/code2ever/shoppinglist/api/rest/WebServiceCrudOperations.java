package com.code2ever.shoppinglist.api.rest;

import java.util.List;

public interface WebServiceCrudOperations {

    <T extends JsonAddEntity> void save(T jsonRequest);

    List<? extends JsonData> get();

    void delete(Long id);

    <T extends JsonUpdateEntity> void update(T jsonRequest);
}
