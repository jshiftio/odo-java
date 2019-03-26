package io.jshift.odo.detectors.util;

public enum BuildTool {
    MAVEN("maven"),
    GRADLE("gradle");

    private final String value;

    BuildTool(String value) {
        this.value = value;
    }
}
