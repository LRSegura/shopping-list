package com.code2ever.shoppinglist.api.exceptions;

import com.code2ever.shoppinglist.api.exceptions.message.ErrorMessage;

public class ApplicationBusinessException extends RuntimeException {

    private final CharSequence errorMessage;

    public ApplicationBusinessException(CharSequence genericMessage){
        super(genericMessage.toString());
        this.errorMessage = genericMessage;
    }

    public ApplicationBusinessException(CharSequence genericMessage, CharSequence detailMessage){
        super(genericMessage + " " + detailMessage);
        this.errorMessage = genericMessage;
    }
}
