package com.lordofthejars.odo.detectors.component;

import com.lordofthejars.odo.detectors.BaseDetector;
import com.lordofthejars.odo.detectors.util.DetectorType;

public abstract class ComponentDetector extends BaseDetector {
    protected ComponentDetector() {super();}

    @Override
    public DetectorType getType() {
        return  DetectorType.COMPONENT;
    }

    protected String getArtifactName() {
        return extractor.extractArtifactId();
    }
}
