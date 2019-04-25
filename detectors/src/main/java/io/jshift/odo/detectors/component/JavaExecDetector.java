package io.jshift.odo.detectors.component;

import io.jshift.odo.detectors.util.Packaging;

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

        odo.createUrl(8080)
            .withComponent(componentName)
            .build()
            .execute(extractor.workingDirectory());

        return componentName;
    }
}
