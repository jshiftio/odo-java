package com.lordofthejars.odo.detectors.extractor;

import com.lordofthejars.odo.detectors.util.Packaging;
import java.nio.file.Path;
import java.util.Set;

public interface Extractor {
    Packaging extractTypeOfProject();
    String extractArtifactId();
    Set<Dependency> extractDependencies();
    Path workingDirectory();
}