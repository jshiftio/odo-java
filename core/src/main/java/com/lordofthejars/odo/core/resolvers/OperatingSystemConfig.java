package com.lordofthejars.odo.core.resolvers;

class OperatingSystemConfig {

    private static final String DIFERENCIA_BINARY_FORMAT = "odo-%s-amd64%s";

    private String osName;

    OperatingSystemConfig(String osName) {
        this.osName = osName;
    }

    String resolveDiferenciaBinary() {
        final OperatingSystemFamily operativeSystem = OperatingSystemFamily.resolve(this.osName);

        String extension = "";

        if (operativeSystem == OperatingSystemFamily.WINDOWS) {
            extension = ".exe";
        }

        return String.format(DIFERENCIA_BINARY_FORMAT, operativeSystem.getLabel(), extension);

    }


}
