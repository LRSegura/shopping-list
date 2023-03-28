package com.code2ever.shoppinglist.api.exceptions;

import com.code2ever.shoppinglist.api.exceptions.message.ErrorMessage;

public class ApplicationBusinessException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public ApplicationBusinessException(ErrorMessage genericMessage){
        super(genericMessage.getDescription());
        this.errorMessage = genericMessage;
    }

    public ApplicationBusinessException(ErrorMessage genericMessage, CharSequence detailMessage){
        super(genericMessage + " " + detailMessage);
        this.errorMessage = genericMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
