package com.lordofthejars.odo.detectors.spi;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.detectors.extractor.Extractor;
import com.lordofthejars.odo.detectors.service.ServiceDetector;
import com.lordofthejars.odo.detectors.util.DetectorType;
import java.util.Optional;

public interface Detector extends Comparable<Detector> {

    int DEFAULT_ORDER = 0;

    /**
     * Sets the order of check. Bigger value means that detector is executed sooner.
     * @return
     */
    default int order() {
        return DEFAULT_ORDER;
    }

    /**
     * If this detector overrides another one, then it is not only required that you add a high priority but also to mark it as an override
     * @return
     */
    default Optional<Class<? extends Detector>> overrides() {
        return Optional.empty();
    }

    @Override
    default int compareTo(Detector o) {
        return o.order() - this.order();
    }

    DetectorType getType();
    void configure(Extractor extractor, Odo odo);
    boolean detect();

    /**
     * Returns the component name used during the execution of commands.
     * @return
     */
    String apply();
}