package com.lordofthejars.odo.detectors.component;

import com.lordofthejars.odo.detectors.util.Packaging;

public class JavaExecDetector extends ComponentDetector {

    public JavaExecDetector() {
        super();
    }

    @Override
    public boolean detect() {

        if (extractor.extractTypeOfProject() != Packaging.JAR) {
            return false;
        }

        return true;
    }

    @Override
    public String apply() {
        final String componentName = getArtifactName();
        odo.createComponent("openjdk18")
            .withComponentName(componentName)
            .withWait().build()
            .execute(extractor.workingDirectory());

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
