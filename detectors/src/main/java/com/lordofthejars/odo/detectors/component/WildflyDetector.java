package com.lordofthejars.odo.detectors.component;


import com.lordofthejars.odo.detectors.util.Packaging;

public class WildflyDetector extends ComponentDetector {
    public WildflyDetector() {
        super();
    }

    @Override
    public boolean detect() {
        if (extractor.extractTypeOfProject() == Packaging.WAR) {
            return true;
        }

        return false;
    }

    @Override
    public String apply() {
        final String componentName = getArtifactName();
        this.odo.createComponent("wildfly")
            .withComponentName(componentName)
            .withWait().build()
            .execute();

        odo.pushComponent().build()
            .execute(extractor.workingDirectory());

        odo.createUrl()
            .withComponent(componentName)
            .withPort(8080)
            .build()
            .execute(extractor.workingDirectory());

        return componentName;
    }
}