package com.code2ever.shoppinglist.api.exceptions;

import com.code2ever.shoppinglist.api.exceptions.message.ErrorMessage;

public class EntityFieldValueException extends EntityFieldException {
    public EntityFieldValueException(){
        super(ErrorMessage.FIELD_VALUE.getDescription());
    }
    public EntityFieldValueException(String message){
        super(message);
    }

}
