package io.jshift.odo.api;

import java.io.InputStream;

public interface LocationResolver {
    InputStream loadResource();

    String getName();
}
