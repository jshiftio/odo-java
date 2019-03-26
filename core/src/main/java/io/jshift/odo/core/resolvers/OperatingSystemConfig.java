package io.jshift.odo.core.resolvers;

class OperatingSystemConfig {

    private static final String ODO_BINARY_FORMAT = "odo-%s-amd64%s";

    private String osName;

    OperatingSystemConfig(String osName) {
        this.osName = osName;
    }

    String resolveOdoBinary() {
        final OperatingSystemFamily operativeSystem = OperatingSystemFamily.resolve(this.osName);

        String extension = "";

        if (operativeSystem == OperatingSystemFamily.WINDOWS) {
            extension = ".exe";
        }

        return String.format(ODO_BINARY_FORMAT, operativeSystem.getLabel(), extension);

    }


}
