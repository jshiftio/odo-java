package io.jshift.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Url {

    private TypeMeta typeMeta;
    private ObjectMeta metadata;

    private UrlSpec spec;

    public static Url from(JsonObject jsonObject) {

        final Url url = new Url();

        final JsonValue spec = jsonObject.get("spec");
        if (spec != null) {
            url.spec = UrlSpec.from(spec.asObject());
        }

        final JsonValue metadata = jsonObject.get("metadata");
        if (metadata != null) {
            url.metadata = ObjectMeta.from(metadata.asObject());
        }

        url.typeMeta = TypeMeta.from(jsonObject);

        return url;

    }

    public TypeMeta getTypeMeta() {
        return typeMeta;
    }

    public ObjectMeta getMetadata() {
        return metadata;
    }

    public UrlSpec getSpec() {
        return spec;
    }
}
