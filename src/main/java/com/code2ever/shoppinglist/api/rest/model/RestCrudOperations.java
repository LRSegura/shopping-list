package com.code2ever.shoppinglist.api.rest.model;

import java.util.List;

public interface RestCrudOperations<T extends JsonData> {
    List<? extends JsonData> restGet();
    void restSave(T jsonRequest);
    void restUpdate(T jsonRequest);
    void restDelete(Long id);
}
