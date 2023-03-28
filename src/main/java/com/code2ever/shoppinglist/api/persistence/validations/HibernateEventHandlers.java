package com.code2ever.shoppinglist.api.persistence.validations;


import com.code2ever.shoppinglist.api.annotations.InjectedDate;
import com.code2ever.shoppinglist.api.util.UtilClass;


import javax.persistence.PrePersist;
import java.lang.reflect.Field;
import java.time.LocalDate;

public class HibernateEventHandlers {

    @PrePersist
    void prePersist(Object entity){
        injectDate(entity);
    }

    private void injectDate(Object entity){
        for (Field field: UtilClass.getFieldsFromEntity(entity)) {
            InjectedDate injectedDate = field.getAnnotation(InjectedDate.class);
            if(injectedDate != null){
                try {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if(value != null && !(value instanceof LocalDate)){
                        throw new IllegalArgumentException("The type of the field for the annotation InjectedDate should be LocalDate");
                    }
                    field.set(entity, LocalDate.now());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Injected Date Error");
                }
            }
        }
    }

}
