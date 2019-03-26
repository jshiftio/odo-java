package io.jshift.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class TypeMeta {

    private String kind;
    private String apiVersion;

    public static TypeMeta from(JsonObject jsonObject) {

        final TypeMeta typeMeta = new TypeMeta();

        final JsonValue kind = jsonObject.get("kind");
        if (kind != null) {
            typeMeta.kind = kind.asString();
        }

        final JsonValue apiVersion = jsonObject.get("apiVersion");
        if (apiVersion != null) {
            typeMeta.apiVersion = apiVersion.asString();
        }

        return typeMeta;
    }

    public String getKind() {
        return kind;
    }

    public String getApiVersion() {
        return apiVersion;
    }
}
