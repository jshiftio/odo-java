package io.jshift.odo.core;

import io.jshift.odo.api.LocationResolver;
import io.jshift.odo.api.OdoConfiguration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import io.jshift.odo.core.resolvers.LocationResolverChain;

public class InstallManager {

    private Path path;
    LocationResolverChain locationResolverChain;

    public InstallManager() {
        this.locationResolverChain = new LocationResolverChain();
    }

    public Path install(OdoConfiguration odoConfiguration) throws IOException {

        final LocationResolver locationResolver = this.locationResolverChain.getLocationResolver(odoConfiguration);

        final FileManager fileManager = new FileManager(locationResolver.getName(),
            locationResolver.loadResource());

        if (odoConfiguration.isInstallationDirSet()) {
            path = fileManager.copyToLocation(odoConfiguration.getInstallationDir());
        } else {
            path = fileManager.copyToTemp();
        }


        FilePermission.execPermission(path);

        return path;
    }

    void uninstall() throws IOException {
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }


}
