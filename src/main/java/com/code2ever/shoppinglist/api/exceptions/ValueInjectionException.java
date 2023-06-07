package com.code2ever.shoppinglist.api.exceptions;

/**
 * RuntimeException to specify all the exceptions occurred at the moment of injecting a field value
 * through reflection.
 *
 * @author Luis
 */
public class ValueInjectionException extends RuntimeException {

    public ValueInjectionException(CharSequence message) {
        super(message.toString());
    }
}
