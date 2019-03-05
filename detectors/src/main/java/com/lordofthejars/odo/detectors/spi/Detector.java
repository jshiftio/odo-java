package com.lordofthejars.odo.detectors.spi;

public interface Detector {

    boolean detect();

    void apply();
}