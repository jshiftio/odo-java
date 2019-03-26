package io.jshift.odo.detectors;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import io.jshift.odo.detectors.spi.Detector;
import io.jshift.odo.detectors.util.DetectorType;

public class DetectorLoader {
    private static ServiceLoader<Detector> loader = ServiceLoader.load(Detector.class);

    public static Iterator<Detector> detectors() {
        return loader.iterator();
    }

    public List<Detector> componentDetectors() {
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(detectors(), Spliterator.ORDERED), false)
            .filter(d -> d.getType() == DetectorType.COMPONENT)
            .collect(Collectors.toList());
    }

    public List<Detector> serviceDetectors() {
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(detectors(), Spliterator.ORDERED), false)
            .filter(d -> d.getType() == DetectorType.SERVICE)
            .collect(Collectors.toList());
    }
}
