package com.lordofthejars.odo.detectors.component;

import com.lordofthejars.odo.detectors.util.Packaging;

public class JavaExecDetector extends ComponentDetector {

    JavaExecDetector() {
        super();
    }

    @Override
    public boolean detect() {

        if (Packaging.valueOf(extractor.extractTypeOfProject()) != Packaging.JAR) {
            return false;
        }

        return true;
    }

    @Override
    public void apply() {
        odo.createComponent("java").withComponentName(getArtifactName()).withWait().build().execute();
        odo.pushComponent().build().execute();
    }
}
