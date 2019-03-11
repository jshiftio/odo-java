package com.lordofthejars.odo.detectors;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.detectors.extractor.Extractor;
import com.lordofthejars.odo.detectors.spi.Detector;
import com.lordofthejars.odo.detectors.util.BuildTool;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DetectorHub {

    private Extractor extractor;
    private Odo odo;

    DetectorHub(Extractor extractor, Odo odo) {
        this.odo = odo;
        this.extractor = extractor;
    }

    public void execute () {

        final List<String> listOfServicesName = detectServices();
        final String componentName = detectComponent();
        linkServices(componentName, listOfServicesName);

        odo.createUrl().withComponent(componentName).build().execute();

    }

    private void linkServices(String componentName, List<String> services) {
        for (String service : services) {
            odo.linkComponent(service)
                .withComponent(componentName)
                .withPort("8080")
                .withWait()
                .build()
                .execute();
        }
    }

    private String detectComponent() {
        String componentName = null;
        final List<Detector> componentDetectors = DetectorLoader.componentDetectors();
        for (Detector detector : componentDetectors) {
            detector.configure(extractor, odo);
            if (detector.detect()) {
                componentName = detector.apply();
                // Only one component
                break;
            }
        }

        return componentName;
    }

    private List<String> detectServices() {
        final List<String> listOfServicesName = new ArrayList<>();
        final List<Detector> serviceDetectors = DetectorLoader.serviceDetectors();
        for (Detector detector : serviceDetectors) {
            detector.configure(extractor, odo);
            if (detector.detect()) {
                listOfServicesName.add(detector.apply());
            }
        }

        return listOfServicesName;
    }

}
