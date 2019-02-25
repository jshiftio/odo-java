package com.lordofthejars.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StorageList implements TerminalOutput {

    private JsonObject internal;

    private TypeMeta typeMeta;
    private ObjectMeta metadata;
    private List<Storage> items = new ArrayList<>();

    public static StorageList from(JsonObject jsonObject) {

        final StorageList storageList = new StorageList();
        storageList.internal = jsonObject;

        final JsonValue items = jsonObject.get("items");
        if (items != null) {
            storageList.items.addAll(items.asArray()
                .values()
                .stream()
                .map(JsonValue::asObject)
                .map(Storage::from)
                .collect(Collectors.toList()));
        }

        final JsonValue metadata = jsonObject.get("metadata");
        if (metadata != null) {
            storageList.metadata = ObjectMeta.from(metadata.asObject());
        }

        storageList.typeMeta = TypeMeta.from(jsonObject);

        return storageList;
    }

    public JsonObject getInternal() {
        return internal;
    }

    public TypeMeta getTypeMeta() {
        return typeMeta;
    }

    public ObjectMeta getMetadata() {
        return metadata;
    }

    public List<Storage> getItems() {
        return items;
    }

    @Override
    public boolean isSimple() {
        return false;
    }
}
