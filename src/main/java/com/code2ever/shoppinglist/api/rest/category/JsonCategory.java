package com.code2ever.shoppinglist.api.rest.category;

import com.code2ever.shoppinglist.api.rest.model.JsonData;

public record JsonCategory(Long id, String description) implements JsonData {
}
