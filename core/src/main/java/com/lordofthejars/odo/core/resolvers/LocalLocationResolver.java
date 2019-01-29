package com.lordofthejars.odo.core.resolvers;

import com.lordofthejars.odo.api.LocationResolver;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalLocationResolver implements LocationResolver {

    private Path location;
    private String name;

    public LocalLocationResolver(Path odoBinary) {
        this.location = odoBinary;
        this.name = odoBinary.getFileName().toString();
    }

    @Override
    public InputStream loadResource() {
        try {
            return new BufferedInputStream(Files.newInputStream(this.location));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }
}
