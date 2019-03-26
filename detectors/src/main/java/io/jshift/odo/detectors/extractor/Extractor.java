package io.jshift.odo.detectors.extractor;

import io.jshift.odo.detectors.util.Packaging;
import java.nio.file.Path;
import java.util.Set;

public interface Extractor {
    Packaging extractTypeOfProject();
    String extractArtifactId();
    Set<Dependency> extractDependencies();
    Path workingDirectory();
}