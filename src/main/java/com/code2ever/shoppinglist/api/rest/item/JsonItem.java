package com.code2ever.shoppinglist.api.rest.item;

import com.code2ever.shoppinglist.api.rest.model.JsonData;

import java.math.BigDecimal;

public record JsonItem(Long id, String name, BigDecimal price, Long idCategory) implements JsonData {
}
