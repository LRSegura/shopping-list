package com.code2ever.shoppinglist.api.rest.model.response;

import com.code2ever.shoppinglist.api.rest.model.JsonData;

import java.util.Collection;

public record JsonDataResponse(Collection<? extends JsonData> data) implements JsonResponse{
}
