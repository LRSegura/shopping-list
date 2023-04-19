package com.code2ever.shoppinglist.api.exceptions;

public class ApplicationBusinessException extends RuntimeException {


    public ApplicationBusinessException(CharSequence genericMessage) {
        super(genericMessage.toString());
    }

    public ApplicationBusinessException(CharSequence genericMessage, CharSequence detailMessage) {
        super(genericMessage + " " + detailMessage);
    }
}
