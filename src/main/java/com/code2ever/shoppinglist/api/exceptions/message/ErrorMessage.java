package com.code2ever.shoppinglist.api.exceptions.message;

public enum ErrorMessage {
    FIELD_VALUE("There is empty fields."),
    FIELD_DECLARATION("There is fields with wrong declaration."),
    USERNAME_IN_USE("Username is in use."),

    DETAIL_LIST_EMPTY("Detail list is empty."),
    DUPLICATED_LIST_NAME("ListName is duplicated");

    private final String description;
    ErrorMessage(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
