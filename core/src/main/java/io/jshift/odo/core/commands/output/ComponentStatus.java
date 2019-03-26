package io.jshift.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;

public class ComponentStatus {

    private boolean active;

    public ComponentStatus(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public static ComponentStatus from(final JsonObject jsonObject) {
        return new ComponentStatus(jsonObject.getBoolean("active", false));
    }

}
