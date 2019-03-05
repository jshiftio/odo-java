package com.lordofthejars.odo.detectors.service;

public class PostgreSQLDetector extends ServiceDetector {
    PostgreSQLDetector() {super();}
    @Override
    public boolean detect() {
        return findWordInDependencies("postgresql");
    }

    @Override
    public void apply() {
        odo.createService("postgresql-persistent", "dev").withWait().build().execute();
    }

}