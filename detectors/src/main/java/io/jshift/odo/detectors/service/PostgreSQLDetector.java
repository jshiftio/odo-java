package io.jshift.odo.detectors.service;

import io.jshift.odo.core.commands.ServiceCreateCommand;
import io.jshift.odo.detectors.extractor.Dependency;
import java.util.Arrays;
import java.util.Optional;

public class PostgreSQLDetector extends ServiceDetector {

    static final Dependency POSTGRESQL_DEPENDENCY = new Dependency("org.postgresql", "postgresql");
    static final Dependency POSTGRESQL_QUARKUS_DEPENDENCY = new Dependency("io.quarkus", "quarkus-jdbc-postgresql");

    private static final String POSTGRESQL_PERSISTENT = "postgresql-persistent";

    private static String POSTGRESQL_USER = "postgresql_user";
    private static String POSTGRESQL_PASSWORD = "postgresql_password";
    private static String POSTGRESQL_DATABASE = "postgresql_url";

    public PostgreSQLDetector() {
        super();
    }

    @Override
    public boolean detect() {
        return isDependencyRegistered(POSTGRESQL_DEPENDENCY) || isDependencyRegistered(POSTGRESQL_QUARKUS_DEPENDENCY);
    }

    @Override
    public String apply() {

        final ServiceCreateCommand.Builder builder = odo.createService(POSTGRESQL_PERSISTENT, "default").withWait();

        final Optional<DatabaseConfiguration> databaseConfigurationExtractor = getDatabaseConfigurationExtractor();

        databaseConfigurationExtractor.ifPresent(databaseConfiguration -> builder.withParameters(
            Arrays.asList(
                getParameter(POSTGRESQL_USER, databaseConfiguration.getUsername()),
                getParameter(POSTGRESQL_PASSWORD, databaseConfiguration.getPassword()),
                getParameter(POSTGRESQL_DATABASE, databaseConfiguration.getDatabase()))));

        builder.build()
            .execute(extractor.workingDirectory());
        return POSTGRESQL_PERSISTENT;
    }

    private String getParameter(String key, String value) {
        return String.format("%s=%s", key, value);
    }

}