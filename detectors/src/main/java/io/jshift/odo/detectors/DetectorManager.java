package io.jshift.odo.detectors;

import io.jshift.odo.core.Odo;
import io.jshift.odo.detectors.spi.Detector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import io.jshift.odo.detectors.extractor.Extractor;

public class DetectorManager {

    private Extractor extractor;
    private Odo odo;

    DetectorLoader detectorLoader;

    public DetectorManager(Extractor extractor, Odo odo) {
        this.odo = odo;
        this.extractor = extractor;
        this.detectorLoader = new DetectorLoader();
    }

    public void execute () {

        final List<String> listOfServicesName = detectServices();
        final String componentName = detectComponent();
        linkServices(componentName, listOfServicesName);

    }

    private void linkServices(String componentName, List<String> services) {
        for (String service : services) {
            odo.linkComponent(service)
                .withComponent(componentName)
                .withWait()
                .build()
                .execute();
        }
    }

    private String detectComponent() {
        String componentName = null;
        final List<Detector> componentDetectors = detectorLoader.componentDetectors();
        Collections.sort(componentDetectors);
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
        final List<Class<? extends Detector>> overridden = new ArrayList<>();

        final List<String> listOfServicesName = new ArrayList<>();
        final List<Detector> serviceDetectors = detectorLoader.serviceDetectors();
        Collections.sort(serviceDetectors);
        for (Detector detector : serviceDetectors) {
            detector.configure(extractor, odo);
            if (isNotOverridden(overridden, detector.getClass()) && detector.detect()) {
                detector.overrides().ifPresent(o -> overridden.add(o));
                listOfServicesName.add(detector.apply());
            }
        }

        return listOfServicesName;
    }

    private boolean isNotOverridden(final List<Class<? extends Detector>> overridden,
        Class<? extends Detector> currentService) {
        return !overridden.contains(currentService);
    }

}
