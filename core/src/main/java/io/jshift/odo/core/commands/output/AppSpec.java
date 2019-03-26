package io.jshift.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.List;
import java.util.stream.Collectors;

public class AppSpec {

    private List<String> components;

    public static final AppSpec from(JsonObject jsonObject) {

        final AppSpec appSpec = new AppSpec();

        final JsonValue components = jsonObject.get("components");
        if (components != null) {
            appSpec.components = components
                .asArray()
                .values()
                .stream()
                .map(JsonValue::asString)
                .collect(Collectors.toList());
        }

        return appSpec;

    }

    public List<String> getComponents() {
        return components;
    }
}
