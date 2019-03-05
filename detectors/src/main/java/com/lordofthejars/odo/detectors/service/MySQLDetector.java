package com.lordofthejars.odo.detectors.service;

public class MySQLDetector extends ServiceDetector {
    public MySQLDetector() {super();}
    @Override
    public boolean detect() {
        return findWordInDependencies("mysql");
    }

    @Override
    public void apply() {
        odo.createService("mysql-persistent", "dev").withWait().build().execute();
    }
}
