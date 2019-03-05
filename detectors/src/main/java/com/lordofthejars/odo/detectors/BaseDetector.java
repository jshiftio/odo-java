package com.lordofthejars.odo.detectors;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.detectors.extractor.Extractor;
import com.lordofthejars.odo.detectors.spi.Detector;

abstract public class BaseDetector implements Detector {

    protected Odo odo;
    protected Extractor extractor;

    public void configure(Extractor extractor, Odo odo) {
        this.odo = odo;
        this.extractor = extractor;
    }
}
