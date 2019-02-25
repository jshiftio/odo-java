package com.lordofthejars.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UrlList implements TerminalOutput {

    private JsonObject internal;

    private TypeMeta typeMeta;
    private ObjectMeta metadata;
    private List<Url> items = new ArrayList<>();

    public static UrlList from(JsonObject jsonObject) {

        final UrlList urlListList = new UrlList();
        urlListList.internal = jsonObject;

        final JsonValue items = jsonObject.get("items");
        if (items != null) {
            urlListList.items.addAll(items.asArray()
                .values()
                .stream()
                .map(JsonValue::asObject)
                .map(Url::from)
                .collect(Collectors.toList()));
        }

        final JsonValue metadata = jsonObject.get("metadata");
        if (metadata != null) {
            urlListList.metadata = ObjectMeta.from(metadata.asObject());
        }

        urlListList.typeMeta = TypeMeta.from(jsonObject);

        return urlListList;
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

    public List<Url> getItems() {
        return items;
    }

    @Override
    public boolean isSimple() {
        return false;
    }
}
