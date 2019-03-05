package com.lordofthejars.odo.detectors.component;


import com.lordofthejars.odo.detectors.util.Packaging;

public class WildflyDetector extends ComponentDetector {
    public WildflyDetector() {
        super();
    }

    @Override
    public boolean detect() {
        if (Packaging.valueOf(extractor.extractTypeOfProject()) == Packaging.WAR) {
            return true;
        }

        return false;
    }

    @Override
    public void apply() {
        this.odo.createComponent("wildfly").withComponentName(getArtifactName()).withWait().build().execute();
        odo.pushComponent().build().execute();
    }
}