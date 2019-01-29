package com.lordofthejars.odo.api;

import java.nio.file.Path;

public class OdoConfiguration {

    private String version;
    private Path localOdo;

    public OdoConfiguration() {

    }

    public boolean isLocalOdoSet() {
        return this.localOdo != null;
    }

    public boolean isVersionSet() {
        return this.version != null && !this.version.isEmpty();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setLocalOdo(Path localOdo) {
        this.localOdo = localOdo;
    }

    public Path getLocalOdo() {
        return localOdo;
    }
}
