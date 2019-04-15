package io.jshift.odo.maven;

import io.jshift.odo.core.commands.AbstractRunnableCommand;
import io.jshift.odo.core.commands.GlobalParametersSupport;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConfigurationInjector {

    private static Logger logger = Logger.getLogger(ConfigurationInjector.class.getName());

    private static List<String> globalParams = new ArrayList<>();

    static {
        final Field[] declaredFields = GlobalParametersSupport.class.getDeclaredFields();
        for (Field f : declaredFields) {
            if (! java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                globalParams.add(f.getName());
            }
        }
    }

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
            } else if (field.getType().equals(List.class)) {
                field.set(command, Arrays.stream(entry.getValue()
                    .split(","))
                    .map(String::trim)
                    .collect(Collectors.toList()));
            } else {
                field.set(command, entry.getValue());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(String.format("Configuration parameter %s is invalid" +
                    " Configuration parameters %s are valid", entry.getKey(), Arrays.toString(c.getFields())));
        }
    }

    public static void injectFields(AbstractRunnableCommand<Void> command, Map<String, String> config) {
        if (config != null) {
            Class<?> c = command.getClass();
            GlobalParametersSupport globalParametersSupport = null;
            for (Map.Entry<String, String> entry : config.entrySet()) {
                try {

                    if (isGlobalParameter(entry.getKey())) {
                        final Field globalParamsField = getGlobalParamsField(command);
                        // If command class contains a global parameter object (all of them should have one, but who knows)
                        if(globalParamsField != null) {
                            if (globalParametersSupport == null) {
                                globalParametersSupport = instantiateGlobalParams(command);
                            }
                            copy(globalParametersSupport, GlobalParametersSupport.class, entry);
                        }

                    } else {
                        copy(command, c, entry);
                    }
                } catch (IllegalStateException | IllegalAccessException exception) {
                    logger.warning(exception.getMessage());
                }
            }
        }
    }

    private static GlobalParametersSupport instantiateGlobalParams(Object command) throws IllegalAccessException {
        final GlobalParametersSupport globalParametersSupport =new GlobalParametersSupport();
        Field globalParam = getGlobalParamsField(command);
        globalParam.setAccessible(true);

        globalParam.set(command, globalParametersSupport);

        return globalParametersSupport;

    }

    private static Field getGlobalParamsField(Object command) {

        final Field[] declaredFields = command.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.getType().equals(GlobalParametersSupport.class)) {
                return declaredField;
            }
        }

        return null;

    }

    private static boolean isGlobalParameter(String name) {
        return globalParams.contains(name);
    }

}
