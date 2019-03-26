package io.jshift.odo.detectors.service;

import io.jshift.odo.detectors.BaseDetector;
import io.jshift.odo.detectors.extractor.Dependency;
import io.jshift.odo.detectors.util.DetectorType;

public abstract class ServiceDetector extends BaseDetector {
    protected ServiceDetector() {
        super();
    }

    @Override
    public DetectorType getType() {
        return DetectorType.SERVICE;
    }

    protected boolean isDependencyRegistered(Dependency dependency) {
        return extractor.extractDependencies().contains(dependency);
    }

}