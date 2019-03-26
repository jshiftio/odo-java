package io.jshift.odo.maven;

import io.jshift.odo.api.OdoConfiguration;
import io.jshift.odo.core.InstallManager;
import io.jshift.odo.core.Odo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import io.jshift.odo.maven.util.DryRunOdoExecutor;

public class OdoFactory {

    static final String ODO = "odo";
    static Path odoLocalPath = Paths.get(System.getProperty("java.io.tmpdir"), ODO);

    private static Odo odo;

    static Odo createOdo(boolean dryRun) {
        if (dryRun) {
            odo = new Odo(new DryRunOdoExecutor());
        } else {
            odo = createOdo();
        }

        return odo;
    }

    static Odo createOdo() {

        if (odo == null) {

            if (!isOdoCopied()) {
                 try {
                     Files.createDirectories(odoLocalPath);
                     final OdoConfiguration odoConfiguration = new OdoConfiguration();
                     odoConfiguration.setInstallationDir(odoLocalPath);

                     final InstallManager installManager = new InstallManager();
                     final Path odoFile = installManager.install(odoConfiguration);

                     Files.move(odoFile, odoLocalPath.resolve(ODO));
                 } catch (IOException e) {
                     throw new IllegalStateException(e);
                 }
            }

            final OdoConfiguration odoConfiguration = new OdoConfiguration();
            odoConfiguration.setLocalOdo(odoLocalPath.resolve(ODO));

            odo = new Odo(odoConfiguration);
        }

        return odo;
    }

    static void removeOdo() {
        try {
            Files.deleteIfExists(odoLocalPath.resolve(ODO));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static boolean isOdoCopied() {
        return Files.exists(odoLocalPath);
    }
}
