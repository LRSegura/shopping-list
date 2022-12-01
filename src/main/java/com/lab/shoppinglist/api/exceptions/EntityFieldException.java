package com.lab.shoppinglist.api.exceptions;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityFieldException extends Exception {

    private final List<String> specificErrorList = new ArrayList<>();

    public EntityFieldException(String message){
        super(message);
    }

    public List<String> getSpecificErrorList() {
        return specificErrorList;
    }

    public void throwIfThereIsSpecificError() throws EntityFieldException {
        if(!specificErrorList.isEmpty()){
            throw this;
        }
    }
}
