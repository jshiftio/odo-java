package com.lordofthejars.odo.detectors;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.detectors.extractor.Extractor;
import com.lordofthejars.odo.detectors.util.BuildTool;

import java.nio.file.Path;

public class DetectorHub {

    private Path buildfile;
    private BuildTool buildTool;
    private Extractor extractor;
    private Odo odo;

    DetectorHub(BuildTool buildTool, Path buildfile, Extractor extractor, Odo odo) {
        this.buildTool = buildTool;
        this.buildfile = buildfile;
        this.odo = odo;
        this.extractor = extractor;
    }

    public void execute () {
        DetectorLoader.detectors().forEachRemaining(detector -> {
            ((BaseDetector)detector).configure(extractor, odo);
            if (detector.detect()) {
                detector.apply();
            }
        });
    }
}
