package com.lordofthejars.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class App implements TerminalOutput {

    private TypeMeta typeMeta;
    private ObjectMeta metadata;

    private AppSpec spec;
    private AppStatus status;

    private JsonObject internal;

    public static final App from(JsonObject jsonObject) {

        final App app = new App();
        app.internal = jsonObject;

        final JsonValue spec = jsonObject.get("spec");
        if (spec != null) {
            app.spec = AppSpec.from(spec.asObject());
        }

        final JsonValue status = jsonObject.get("status");
        if (status != null) {
            app.status = AppStatus.from(status.asObject());
        }

        final JsonValue metadata = jsonObject.get("metadata");
        if (metadata != null) {
            app.metadata = ObjectMeta.from(metadata.asObject());
        }

        app.typeMeta = TypeMeta.from(jsonObject);

        return app;

    }

    public TypeMeta getTypeMeta() {
        return typeMeta;
    }

    public ObjectMeta getMetadata() {
        return metadata;
    }

    public AppSpec getSpec() {
        return spec;
    }

    public AppStatus getStatus() {
        return status;
    }

    public JsonObject getInternal() {
        return internal;
    }

    @Override
    public boolean isSimple() {
        return true;
    }
}
