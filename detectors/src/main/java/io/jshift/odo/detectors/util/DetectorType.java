package io.jshift.odo.detectors.util;

public enum DetectorType {
    COMPONENT("component"),
    SERVICE("service");

    private final String value;

    DetectorType(String value) {
        this.value = value;
    }
}
