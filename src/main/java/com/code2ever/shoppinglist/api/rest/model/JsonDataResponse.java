package com.code2ever.shoppinglist.api.rest.model;

import java.util.Collection;

public record JsonDataResponse(Collection<? extends JsonData> data) implements JsonResponse{
}
