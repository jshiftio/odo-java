package com.lordofthejars.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;

public class StorageStatus {

    private boolean mounted;

    public StorageStatus(boolean mounted) {
        this.mounted = mounted;
    }

    public boolean isMounted() {
        return mounted;
    }

    public static StorageStatus from(JsonObject jsonObject) {
        return new StorageStatus(jsonObject.getBoolean("mounted", false));
    }
}
