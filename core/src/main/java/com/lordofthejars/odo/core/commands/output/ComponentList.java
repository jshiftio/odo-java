package com.lordofthejars.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentList implements TerminalOutput{

    private JsonObject internal;

    private TypeMeta typeMeta;
    private ObjectMeta metadata;
    private List<Component> items = new ArrayList<>();

    public static ComponentList from(JsonObject jsonObject) {

        final ComponentList componentList = new ComponentList();
        componentList.internal = jsonObject;

        final JsonValue items = jsonObject.get("items");
        if (items != null) {
            componentList.items.addAll(items.asArray()
                .values()
                .stream()
                .map(JsonValue::asObject)
                .map(Component::from)
                .collect(Collectors.toList()));
        }

        final JsonValue metadata = jsonObject.get("metadata");
        if (metadata != null) {
            componentList.metadata = ObjectMeta.from(metadata.asObject());
        }

        componentList.typeMeta = TypeMeta.from(jsonObject);

        return componentList;
    }

    public JsonObject getInternal() {
        return internal;
    }

    public ObjectMeta getMetadata() {
        return metadata;
    }

    public TypeMeta getTypeMeta() {
        return typeMeta;
    }

    public List<Component> getItems() {
        return items;
    }

    @Override
    public boolean isSimple() {
        return false;
    }
}
