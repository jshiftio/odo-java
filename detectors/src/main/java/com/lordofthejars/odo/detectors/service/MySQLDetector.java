package com.lordofthejars.odo.detectors.service;

import com.lordofthejars.odo.detectors.extractor.Dependency;

public class MySQLDetector extends ServiceDetector {

    static final Dependency MYSQL_DEPENDENCY = new Dependency("mysql", "mysql-connector-java");
    private static final String MYSQL_PERSISTENT = "mysql-persistent";

    public MySQLDetector() {
        super();
    }

    @Override
    public boolean detect() {
        return isDependencyRegistered(MYSQL_DEPENDENCY);
    }

    @Override
    public String apply() {
        odo.createService(MYSQL_PERSISTENT, "default").withWait().build()
            .execute(extractor.workingDirectory());
        return MYSQL_PERSISTENT;
    }
}
