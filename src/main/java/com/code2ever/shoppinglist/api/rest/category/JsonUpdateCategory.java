package com.code2ever.shoppinglist.api.rest.category;

import com.code2ever.shoppinglist.api.rest.JsonData;
import com.code2ever.shoppinglist.api.rest.JsonUpdateEntity;

public record JsonUpdateCategory(Long id, String description) implements JsonData, JsonUpdateEntity {
}
