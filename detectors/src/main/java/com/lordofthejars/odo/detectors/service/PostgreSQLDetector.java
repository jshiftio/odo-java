package com.lordofthejars.odo.detectors.service;

import com.lordofthejars.odo.detectors.extractor.Dependency;

public class PostgreSQLDetector extends ServiceDetector {

    static final Dependency POSTGRESQL_DEPENDENCY = new Dependency("org.postgresql", "postgresql");
    private static final String POSTGRESQL_PERSISTENT = "postgresql-persistent";

    public PostgreSQLDetector() {
        super();
    }

    @Override
    public boolean detect() {
        return isDependencyRegistered(POSTGRESQL_DEPENDENCY);
    }

    @Override
    public String apply() {
        odo.createService(POSTGRESQL_PERSISTENT, "default").withWait().build()
            .execute(extractor.workingDirectory());
        return POSTGRESQL_PERSISTENT;
    }

}