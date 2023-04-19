package com.code2ever.shoppinglist.api.util;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class UtilClass {

    public static <T> Set<Field> getFieldsFromEntity(T entity) {
        Class<?> entityClass = entity.getClass();
        Set<Field> fields = Arrays.stream(entityClass.getDeclaredFields()).collect(Collectors.toSet());
        Class<?> entityClassParent = entityClass.getSuperclass();
        while (!entityClassParent.equals(Object.class)) {
            fields.addAll(Arrays.stream(entityClassParent.getDeclaredFields()).collect(Collectors.toSet()));
            entityClassParent = entityClassParent.getSuperclass();
        }
        return fields;
    }

    public static void requireNonBlankString(String string, String message) {
        if (string.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireNonBlankString(String string) {
        requireNonBlankString(string, "The passed string is empty!");
    }

}
