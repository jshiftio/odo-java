package io.jshift.odo.detectors.service;

import io.jshift.odo.detectors.extractor.Dependency;
import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.spi.DatabaseConfigurationExtractor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

public class QuarkusDatabaseExtractor implements DatabaseConfigurationExtractor {

    private static final String QUARKUS_DATASOURCE_USERNAME = "quarkus.datasource.username";
    private static final String QUARKUS_DATASOURCE_PASSWORD = "quarkus.datasource.password";
    private static final String QUARKUS_DATASOURCE_URL = "quarkus.datasource.url";

    private static Path DEFAULT_LOCATION = Paths.get("src/main/resources");

    static final Dependency QUARKUS_DEPENDENCY =
        new Dependency("io.quarkus", "quarkus-arc");

    Path application = DEFAULT_LOCATION.resolve("application.properties");

    private Extractor extractor;

    @Override
    public Optional<DatabaseConfiguration> extract() {

        if (extractor.extractDependencies().contains(QUARKUS_DEPENDENCY)) {

            if (Files.exists(application)) {
                final Properties properties = new Properties();
                try {
                    properties.load(Files.newInputStream(application));
                    return Optional.ofNullable(getConfiguration(properties));
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public void setExtractor(Extractor extractor) {
        this.extractor = extractor;
    }

    private DatabaseConfiguration getConfiguration(Properties properties) {
        String username = "";
        String password = "";
        String url = "";

        if (properties.containsKey(QUARKUS_DATASOURCE_USERNAME)) {
            username = properties.getProperty(QUARKUS_DATASOURCE_USERNAME);
        }

        if (properties.containsKey(QUARKUS_DATASOURCE_PASSWORD)) {
            password = properties.getProperty(QUARKUS_DATASOURCE_PASSWORD);
        }

        if (properties.containsKey(QUARKUS_DATASOURCE_URL)) {
            url = properties.getProperty(QUARKUS_DATASOURCE_URL);
        }

        if (username.isEmpty() && password.isEmpty() && url.isEmpty()) {
            return null;
        }

        return new DatabaseConfiguration(username, password, JdbcUrlParser.getDatabase(url));
    }

}
