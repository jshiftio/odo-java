package io.jshift.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ObjectMeta {

    private String name;
    private String namespace;

    public static ObjectMeta from(JsonObject jsonObject) {

        final ObjectMeta objectMeta = new ObjectMeta();

        final JsonValue name = jsonObject.get("name");
        if (name != null) {
            objectMeta.name = name.asString();
        }

        final JsonValue namespace = jsonObject.get("namespace");
        if (namespace != null) {
            objectMeta.namespace = namespace.asString();
        }

        return objectMeta;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }
}
