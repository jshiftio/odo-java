package io.jshift.odo.core.resolvers;

import io.jshift.odo.api.LocationResolver;
import java.io.InputStream;

public class ClasspathLocationResolver implements LocationResolver {

    private static final String PACKAGE_LOCATION = "binaries";

    private String fileclasspath;
    private String name;

    public ClasspathLocationResolver(String odoBinary) {
        this.fileclasspath = PACKAGE_LOCATION + "/" + odoBinary;
        this.name = odoBinary;
    }

    @Override
    public InputStream loadResource() {
        return Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(this.fileclasspath);
    }

    @Override
    public String getName() {
        return name;
    }
}
