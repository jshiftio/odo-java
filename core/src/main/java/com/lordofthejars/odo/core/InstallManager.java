package com.lordofthejars.odo.core;

import com.lordofthejars.odo.api.LocationResolver;
import com.lordofthejars.odo.api.OdoConfiguration;
import com.lordofthejars.odo.core.resolvers.LocationResolverChain;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class InstallManager {

    private Path copyToTemp;
    LocationResolverChain locationResolverChain;

    InstallManager() {
        this.locationResolverChain = new LocationResolverChain();
    }

    Path install(OdoConfiguration odoConfiguration) throws IOException {

        final LocationResolver locationResolver = this.locationResolverChain.getLocationResolver(odoConfiguration);

        final FileManager fileManager = new FileManager(locationResolver.getName(),
            locationResolver.loadResource());
        copyToTemp = fileManager.copyToTemp();

        FilePermission.execPermission(copyToTemp);

        return copyToTemp;
    }

    void uninstall() throws IOException {
        if (Files.exists(copyToTemp)) {
            Files.delete(copyToTemp);
        }
    }


}
