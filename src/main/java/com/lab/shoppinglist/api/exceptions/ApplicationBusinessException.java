package com.lab.shoppinglist.api.exceptions;

import com.lab.shoppinglist.api.exceptions.message.ErrorMessage;

public class ApplicationBusinessException extends RuntimeException {

    public ApplicationBusinessException(ErrorMessage genericMessage){
        super(genericMessage.getDescription());
    }

    public ApplicationBusinessException(ErrorMessage genericMessage, CharSequence detailMessage){
        super(genericMessage + " " + detailMessage);
    }
}
