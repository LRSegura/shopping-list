package com.code2ever.shoppinglist.api.rest.list;

import com.code2ever.shoppinglist.api.rest.model.JsonData;

import java.math.BigDecimal;

public record JsonList(Long id, String name, BigDecimal total, Integer totalItems) implements JsonData {
}
