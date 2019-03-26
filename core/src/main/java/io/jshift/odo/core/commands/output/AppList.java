package io.jshift.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppList implements TerminalOutput {

    private JsonObject internal;

    private TypeMeta typeMeta;
    private ObjectMeta metadata;
    private List<App> items = new ArrayList<>();

    public static final AppList from (JsonObject jsonObject) {

        final AppList appList = new AppList();
        appList.internal = jsonObject;

        final JsonValue items = jsonObject.get("items");
        if (items != null) {
            appList.items.addAll(items.asArray()
                .values()
                .stream()
                .map(JsonValue::asObject)
                .map(App::from)
                .collect(Collectors.toList()));
        }

        final JsonValue metadata = jsonObject.get("metadata");
        if (metadata != null) {
            appList.metadata = ObjectMeta.from(metadata.asObject());
        }

        appList.typeMeta = TypeMeta.from(jsonObject);

        return appList;

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

    public List<App> getItems() {
        return items;
    }

    @Override
    public boolean isSimple() {
        return false;
    }
}
