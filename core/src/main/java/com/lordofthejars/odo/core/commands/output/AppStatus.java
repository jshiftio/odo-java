package com.lordofthejars.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;

public class AppStatus {

    private boolean active;

    public AppStatus(boolean active) {
        this.active = active;
    }

    public static final AppStatus from(JsonObject jsonObject) {
        return new AppStatus(jsonObject.getBoolean("active", false));
    }

    public boolean isActive() {
        return active;
    }
}
