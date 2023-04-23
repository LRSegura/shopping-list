package com.code2ever.shoppinglist.security.user;

import com.code2ever.shoppinglist.api.rest.model.JsonData;

public record JsonUser(Long id, String firstName, String lastName, String userName, String password) implements JsonData {
}
