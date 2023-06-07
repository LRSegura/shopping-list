package com.code2ever.shoppinglist.api.rest.detail;

import com.code2ever.shoppinglist.api.rest.model.JsonData;

import java.math.BigDecimal;

public record JsonAddedDetail(Long idDetail, String itemName, BigDecimal total, Integer amount, Boolean bought)  implements JsonData {
}
