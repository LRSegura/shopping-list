package com.lab.shoppinglist.views;

public enum ShowMessagesForm {
    ADDED_ELEMENT("addedElement"),
    DELETED_ELEMENT("deletedElement"),
    DUPLICATED_ELEMENT("duplicatedElement");

    private final String description;
    private ShowMessagesForm(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
