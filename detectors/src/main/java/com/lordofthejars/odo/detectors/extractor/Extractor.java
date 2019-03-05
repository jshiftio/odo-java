package com.lordofthejars.odo.detectors.extractor;

import java.util.List;

public interface Extractor {
    String extractTypeOfProject();
    String extractArtifactId();
    List<Dependency> extractDependencies();
}