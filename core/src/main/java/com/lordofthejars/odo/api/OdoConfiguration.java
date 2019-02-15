package com.lordofthejars.odo.api;

import java.nio.file.Path;

public class OdoConfiguration {

    private String version;
    private Path localOdo;

    private boolean printStandardStreamToConsole = true;
    private boolean printErrorStreamToConsole = true;

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

    public boolean isPrintStandardStreamToConsole() {
        return printStandardStreamToConsole;
    }

    public void setPrintStandardStreamToConsole(boolean printStandardStreamToConsole) {
        this.printStandardStreamToConsole = printStandardStreamToConsole;
    }

    public boolean isPrintErrorStreamToConsole() {
        return printErrorStreamToConsole;
    }

    public void setPrintErrorStreamToConsole(boolean printErrorStreamToConsole) {
        this.printErrorStreamToConsole = printErrorStreamToConsole;
    }
}
