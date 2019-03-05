package com.lordofthejars.odo.maven;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurationInjector {
    public static void copy(Object command, Class<?> c, Map.Entry<String, String> entry) {
        try {
            Field field = c.getDeclaredField(entry.getKey());
            field.setAccessible(true);
            if (field.getType().equals(Integer.class)) {
                field.setLong(command, Integer.parseInt(entry.getValue()));
            } else if (field.getType().equals(Boolean.class)) {
                field.setBoolean(command, Boolean.parseBoolean(entry.getValue()));
            }
            else if (field.getType().equals(List.class)) {
                field.set(command, Arrays.stream(entry.getValue()
                        .split(","))
                        .map(String::trim)
                        .collect(Collectors.toList()));
            } else {
                field.set(command, entry.getValue());
            }
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new IllegalStateException(String.format("Configuration parameter %s is invalid" +
                    " Configuration parameters %s are valid", entry.getKey(), Arrays.toString(c.getFields())));
        }
    }
}
