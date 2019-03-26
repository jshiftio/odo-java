package io.jshift.odo.detectors;

import io.jshift.odo.core.Odo;
import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.spi.Detector;

abstract public class BaseDetector implements Detector {

    protected Odo odo;
    protected Extractor extractor;

    @Override
    public void configure(Extractor extractor, Odo odo) {
        this.odo = odo;
        this.extractor = extractor;
    }
}
