package com.code2ever.shoppinglist.api.rest.category;

import com.code2ever.shoppinglist.api.rest.JsonData;

public record JsonUpdateCategory(Long id, String description) implements JsonData {
}
