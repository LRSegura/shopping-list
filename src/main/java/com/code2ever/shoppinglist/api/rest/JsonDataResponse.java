package com.code2ever.shoppinglist.api.rest;

import java.util.Collection;

public record JsonDataResponse(Collection<? extends JsonData> data) implements JsonResponse{
}
