package io.jshift.odo.detectors.component;

import io.jshift.odo.detectors.BaseDetector;
import io.jshift.odo.detectors.util.DetectorType;

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
