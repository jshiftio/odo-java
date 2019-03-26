package io.jshift.odo.core.resolvers;

import io.jshift.odo.api.LocationResolver;
import io.jshift.odo.api.OdoConfiguration;

public class LocationResolverChain {

    static final String ODO_VERSION = "odo.version";
    OperatingSystemConfig operatingSystemConfig;

    public LocationResolverChain() {
        this.operatingSystemConfig = new OperatingSystemConfig(System.getProperty("os.name"));
    }

    public LocationResolver getLocationResolver(OdoConfiguration odoConfiguration) {

        final String odoBinary = this.operatingSystemConfig.resolveOdoBinary();

        if (System.getProperty(ODO_VERSION) != null) {
            return getUrlLocationResolver(odoBinary, System.getProperty(ODO_VERSION));
        }

        if (odoConfiguration.isVersionSet()) {
            return getUrlLocationResolver(odoBinary, odoConfiguration.getVersion());
        }

        return getClasspathLocationResolver(odoBinary);
    }

    private LocationResolver getClasspathLocationResolver(String odoBinary) {
        return new ClasspathLocationResolver(odoBinary);
    }

    private LocationResolver getUrlLocationResolver(String odoBinary, String version) {
        return new UrlLocationResolver(odoBinary, version);
    }

}
