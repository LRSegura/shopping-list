package com.code2ever.shoppinglist.api.rest;

import java.util.Collection;

public record JsonDataResponse<T>(Collection<T> data) implements JsonResponse{
}
