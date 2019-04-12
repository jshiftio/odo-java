package io.jshift.odo.detectors.service;

import io.jshift.odo.core.commands.ServiceCreateCommand;
import io.jshift.odo.detectors.extractor.Dependency;
import java.util.Arrays;
import java.util.Optional;

public class PostgreSQLDetector extends ServiceDetector {

    static final Dependency POSTGRESQL_DEPENDENCY = new Dependency("org.postgresql", "postgresql");
    private static final String POSTGRESQL_PERSISTENT = "postgresql-persistent";

    private static String POSTGRESQL_USER = "postgresql_user";
    private static String POSTGRESQL_PASSWORD = "postgresql_password";

    public PostgreSQLDetector() {
        super();
    }

    @Override
    public boolean detect() {
        return isDependencyRegistered(POSTGRESQL_DEPENDENCY);
    }

    @Override
    public String apply() {

        final ServiceCreateCommand.Builder builder = odo.createService(POSTGRESQL_PERSISTENT, "default").withWait();

        final Optional<DatabaseConfiguration> databaseConfigurationExtractor = getDatabaseConfigurationExtractor();

        databaseConfigurationExtractor.ifPresent(databaseConfiguration -> builder.withParameters(
            Arrays.asList(
                getParameter(POSTGRESQL_USER, databaseConfiguration.getUsername()),
                getParameter(POSTGRESQL_PASSWORD, databaseConfiguration.getPassword()))));

        builder.build()
            .execute(extractor.workingDirectory());
        return POSTGRESQL_PERSISTENT;
    }

    private String getParameter(String key, String value) {
        return String.format("%s=%s", key, value);
    }

}