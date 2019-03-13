package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.commands.AbstractRunnableCommand;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConfigurationInjector {

    private static Logger logger = Logger.getLogger(ConfigurationInjector.class.getName());

    public static void copy(Object command, Class<?> c, Map.Entry<String, String> entry) {
        try {
            Field field = c.getDeclaredField(entry.getKey());
            field.setAccessible(true);
            if (field.getType().equals(int.class)) {
                field.setInt(command, Integer.parseInt(entry.getValue()));
            } else if (field.getType().equals(Integer.class)) {
                field.set(command, Integer.valueOf(Integer.parseInt(entry.getValue())));
            } else if (field.getType().equals(boolean.class)) {
                field.setBoolean(command, Boolean.parseBoolean(entry.getValue()));
            } else if (field.getType().equals(Boolean.class)) {
                field.set(command, Boolean.valueOf(Boolean.parseBoolean(entry.getValue())));
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

    public static void injectFields(AbstractRunnableCommand<Void> command, Map<String, String> config) {
        if (config != null) {
            Class<?> c = command.getClass();
            for (Map.Entry<String, String> entry : config.entrySet()) {
                try {
                    copy(command, c, entry);
                } catch (IllegalStateException exception) {
                    logger.warning(exception.getMessage());
                }
            }
        }
    }
}
