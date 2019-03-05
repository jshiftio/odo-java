package com.lordofthejars.odo.detectors.service;

import com.lordofthejars.odo.detectors.BaseDetector;
import com.lordofthejars.odo.detectors.extractor.Dependency;
import com.lordofthejars.odo.detectors.util.DetectorType;

import java.util.List;

public abstract class ServiceDetector extends BaseDetector {
    protected ServiceDetector() {
        super();
    }

    public DetectorType getType() {
        return DetectorType.SERVICE;
    }

    protected boolean findWordInDependencies(String s) {
        List<Dependency> deps = extractor.extractDependencies();
        for (Dependency dep : deps) {
            if (dep.depName().toLowerCase().startsWith(s)) return true;
        }
        return false;
    }

}