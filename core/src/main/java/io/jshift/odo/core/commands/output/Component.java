package io.jshift.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Component implements TerminalOutput {

    private TypeMeta typeMeta;
    private ObjectMeta metadata;
    private ComponentSpec spec;
    private ComponentStatus status;

    private JsonObject internal;

    public static final Component from(JsonObject jsonObject) {

        final Component component = new Component();
        component.internal = jsonObject;

        final JsonValue spec = jsonObject.get("spec");
        if (spec != null) {
            component.spec = ComponentSpec.from(spec.asObject());
        }

        final JsonValue status = jsonObject.get("status");
        if (status != null) {
            component.status = ComponentStatus.from(status.asObject());
        }

        final JsonValue metadata = jsonObject.get("metadata");
        if (metadata != null) {
            component.metadata = ObjectMeta.from(metadata.asObject());
        }

        component.typeMeta = TypeMeta.from(jsonObject);

        return component;
    }

    public TypeMeta getTypeMeta() {
        return typeMeta;
    }

    public ComponentSpec getSpec() {
        return spec;
    }

    public ComponentStatus getStatus() {
        return status;
    }

    public ObjectMeta getMetadata() {
        return metadata;
    }

    public JsonObject getInternal() {
        return internal;
    }

    @Override
    public boolean isSimple() {
        return true;
    }
}
