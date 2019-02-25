package com.lordofthejars.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComponentSpec {

    private String type;
    private String source;
    private List<String> url = new ArrayList<>();
    private List<String> storage = new ArrayList<>();
    private Map<String, Object> env = new HashMap<>();

    public static ComponentSpec from(final JsonObject jsonObject) {

        final ComponentSpec componentSpec = new ComponentSpec();

        final JsonValue type = jsonObject.get("type");
        if (type != null) {
            componentSpec.type = type.asString();
        }

        final JsonValue source = jsonObject.get("source");
        if (type != null) {
            componentSpec.source = source.asString();
        }

        final JsonValue url = jsonObject.get("url");
        if (url != null) {
            componentSpec.url.addAll(url.asArray()
                .values()
                .stream()
                .map(JsonValue::asString)
                .collect(Collectors.toList()));
        }

        final JsonValue storage = jsonObject.get("storage");
        if (storage != null) {
            componentSpec.storage.addAll(storage.asArray()
                .values()
                .stream()
                .map(JsonValue::asString)
                .collect(Collectors.toList()));
        }

        final JsonValue env = jsonObject.get("env");
        if (env != null) {
            env.asArray()
                .values()
                .stream()
                .map(JsonValue::asObject)
                .collect(
                    Collectors.toMap(
                        j -> {
                            final JsonValue name = j.get("name");
                            if (name != null) {
                                return name.asString();
                            }
                            return null;
                        },
                        j -> {
                            final JsonValue value = j.get("value");
                            if (value != null) {
                                return value.asString();
                            }
                            return null;
                        }
                    )
                );
        }

        return componentSpec;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public List<String> getUrl() {
        return url;
    }

    public List<String> getStorage() {
        return storage;
    }

    public Map<String, Object> getEnv() {
        return env;
    }

}
