package com.lordofthejars.odo.detectors.service;

import com.lordofthejars.odo.detectors.BaseDetector;
import com.lordofthejars.odo.detectors.extractor.Dependency;
import com.lordofthejars.odo.detectors.util.DetectorType;

import java.util.List;

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