package com.code2ever.shoppinglist.api.rest.detail;

import com.code2ever.shoppinglist.api.rest.model.JsonData;

public record JsonDetail(Long id, Long idItem, Integer amount, Boolean bought, Long idList) implements JsonData {
}
