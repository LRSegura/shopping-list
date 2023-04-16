package com.code2ever.shoppinglist.api.exceptions;

import com.code2ever.shoppinglist.api.exceptions.message.ErrorMessage;

public class EntityFieldDeclarationException extends EntityFieldException {
    public EntityFieldDeclarationException() {
        super(ErrorMessage.FIELD_DECLARATION.getDescription());
    }

    public EntityFieldDeclarationException(String message) {
        super(message);
    }
}
