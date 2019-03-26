package io.jshift.odo.core.resolvers;

import io.jshift.odo.api.LocationResolver;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlLocationResolver implements LocationResolver {

    static final String RELEASE_URL = "https://github.com/redhat-developer/odo/releases/download/v%s/%s";

    private String name;
    private URL url;

    public UrlLocationResolver(String url) {
        this.name = url.substring(url.lastIndexOf('/') + 1);
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public UrlLocationResolver(String odoBinary, String version) {
        this.name = odoBinary;
        try {
            this.url = new URL(String.format(RELEASE_URL, version, odoBinary));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public InputStream loadResource() {
        try {
            return new BufferedInputStream(url.openStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
