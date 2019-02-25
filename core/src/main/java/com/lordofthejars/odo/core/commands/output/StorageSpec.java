package com.lordofthejars.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class StorageSpec {

    private String size;
    private String path;

    public static StorageSpec from(JsonObject jsonObject) {
        final StorageSpec storageSpec = new StorageSpec();

        final JsonValue size = jsonObject.get("size");
        if (size != null) {
            storageSpec.size = size.asString();
        }

        final JsonValue path = jsonObject.get("path");
        if (path != null) {
            storageSpec.path = path.asString();
        }

        return  storageSpec;
    }

    public String getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }
}
