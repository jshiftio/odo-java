package com.lordofthejars.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Storage {

    private TypeMeta typeMeta;
    private ObjectMeta metadata;

    private StorageSpec spec;
    private StorageStatus status;

    public static Storage from (JsonObject jsonObject) {

        final Storage storage = new Storage();

        final JsonValue spec = jsonObject.get("spec");
        if (spec != null) {
            storage.spec = StorageSpec.from(spec.asObject());
        }

        final JsonValue status = jsonObject.get("status");
        if (status != null) {
            storage.status = StorageStatus.from(status.asObject());
        }

        final JsonValue metadata = jsonObject.get("metadata");
        if (metadata != null) {
            storage.metadata = ObjectMeta.from(metadata.asObject());
        }

        storage.typeMeta = TypeMeta.from(jsonObject);

        return storage;

    }

    public TypeMeta getTypeMeta() {
        return typeMeta;
    }

    public ObjectMeta getMetadata() {
        return metadata;
    }

    public StorageSpec getSpec() {
        return spec;
    }

    public StorageStatus getStatus() {
        return status;
    }
}
