package com.code2ever.shoppinglist.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to inject de current date to the applied field. This annotation must be used in an entity field.
 * The date is injected at the moment of persisting the entity by the
 * {@link com.code2ever.shoppinglist.api.persistence.validations.HibernateEventHandlers} listener.
 *
 * @author Luis
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectedDate {
}
