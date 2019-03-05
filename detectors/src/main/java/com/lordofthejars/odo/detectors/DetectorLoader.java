package com.lordofthejars.odo.detectors;

import com.lordofthejars.odo.detectors.spi.Detector;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DetectorLoader {
    private static ServiceLoader<Detector> loader = ServiceLoader.load(Detector.class);

    public static Iterator<Detector> detectors() {
        loader.reload();
        return loader.iterator();
    }
}
