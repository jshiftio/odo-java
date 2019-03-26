package io.jshift.odo.core.resolvers;

enum OperatingSystemFamily {

    LINUX("linux", "linux"),
    WINDOWS("windows", "windows"),
    MAC("darwin", "mac");

    // Label used by system property os.name
    private String label;
    // OsName for diferencia
    private String osName;

    OperatingSystemFamily(String label, String osName) {
        this.label = label;
        this.osName = osName;
    }

    static OperatingSystemFamily resolve(String osName) {
        for (OperatingSystemFamily operatingSystemFamily: OperatingSystemFamily.values()) {
            if (osName.toLowerCase().startsWith(operatingSystemFamily.osName)) {
                return operatingSystemFamily;
            }
        }

        throw new IllegalArgumentException(String.format("No operating system supported %s", osName));

    }

    String getLabel() {
        return label;
    }

}
