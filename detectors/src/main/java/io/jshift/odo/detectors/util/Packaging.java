package io.jshift.odo.detectors.util;

public enum Packaging {

    WAR("war"),
    JAR("jar"),
    MAVEN_PLUGIN("maven-plugin"),
    EJB("ejb"),
    EAR("ear"),
    RAR("rar"),
    PAR("par");

    private final String value;

    Packaging(String value) {
        this.value = value;
    }
}
