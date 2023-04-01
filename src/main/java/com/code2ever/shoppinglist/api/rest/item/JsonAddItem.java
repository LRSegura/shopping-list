package com.code2ever.shoppinglist.api.rest.item;

import com.code2ever.shoppinglist.api.rest.JsonData;

import java.math.BigDecimal;

public record JsonAddItem(String name, BigDecimal price, Long idCategory) implements JsonData {
}
