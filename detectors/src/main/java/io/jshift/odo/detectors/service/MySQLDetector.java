package io.jshift.odo.detectors.service;

import io.jshift.odo.core.commands.ServiceCreateCommand;
import io.jshift.odo.detectors.extractor.Dependency;
import java.util.Arrays;
import java.util.Optional;

public class MySQLDetector extends ServiceDetector {

    static final Dependency MYSQL_DEPENDENCY = new Dependency("mysql", "mysql-connector-java");
    private static final String MYSQL_PERSISTENT = "mysql-persistent";

    private static String MYSQL_USER = "mysql_user";
    private static String MYSQL_PASSWORD = "mysql_password";
    private static String MYSQL_DATABASE = "mysql_database";

    public MySQLDetector() {
        super();
    }

    @Override
    public boolean detect() {
        return isDependencyRegistered(MYSQL_DEPENDENCY);
    }

    @Override
    public String apply() {
        final ServiceCreateCommand.Builder builder = odo.createService(MYSQL_PERSISTENT, "default").withWait();

        final Optional<DatabaseConfiguration> databaseConfigurationExtractor = getDatabaseConfigurationExtractor();

        databaseConfigurationExtractor.ifPresent(databaseConfiguration -> builder.withParameters(
            Arrays.asList(
                getParameter(MYSQL_USER, databaseConfiguration.getUsername()),
                getParameter(MYSQL_PASSWORD, databaseConfiguration.getPassword()),
                getParameter(MYSQL_DATABASE, databaseConfiguration.getDatabase()))));

        builder.build()
            .execute(extractor.workingDirectory());
        return MYSQL_PERSISTENT;
    }

    private String getParameter(String key, String value) {
        return String.format("%s=%s", key, value);
    }
}
