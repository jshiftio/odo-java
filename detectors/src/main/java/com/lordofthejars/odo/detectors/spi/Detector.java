package com.lordofthejars.odo.detectors.spi;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.detectors.extractor.Extractor;
import com.lordofthejars.odo.detectors.util.DetectorType;

public interface Detector {

    DetectorType getType();
    void configure(Extractor extractor, Odo odo);
    boolean detect();

    /**
     * Returns the component name used during the execution of commands.
     * @return
     */
    String apply();
}