package io.jshift.odo.detectors.service;

import io.jshift.odo.detectors.extractor.Dependency;
import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.spi.DatabaseConfigurationExtractor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import org.yaml.snakeyaml.Yaml;

public class SpringBootDatabaseExtractor implements DatabaseConfigurationExtractor {

    static final Dependency SPRING_BOOT_JPA_DEPENDENCY =
        new Dependency("org.springframework.boot", "spring-boot-starter-data-jpa");

    private static Path DEFAULT_LOCATION = Paths.get("src/main/resources");

    Path properties = DEFAULT_LOCATION.resolve("application.properties");
    Path yml = DEFAULT_LOCATION.resolve("application.yml");
    Path yaml = DEFAULT_LOCATION.resolve("application.yaml");

    private Extractor extractor;

    @Override
    public Optional<DatabaseConfiguration> extract() {

        // It is a Spring Boot JPA service, let's check for Spring Boot conf
        if (extractor.extractDependencies().contains(SPRING_BOOT_JPA_DEPENDENCY)) {
            try {
                return Optional.ofNullable(getConfiguration());
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return Optional.empty();
    }

    @Override
    public void setExtractor(Extractor extractor) {
        this.extractor = extractor;
    }

    private DatabaseConfiguration getConfiguration() throws IOException {
        if (Files.exists(properties)) {
            final Properties properties = new Properties();
            properties.load(Files.newInputStream(this.properties));
            return PropertiesExtractor.extract(properties);
        } else {
            if (Files.exists(yaml)) {
                final Yaml yaml = new Yaml();
                Map<String, Object> properties = yaml.loadAs(Files.newInputStream(this.yaml), Map.class);
                return YamlExtractor.extract(properties);
            } else {
                if (Files.exists(yml)) {
                    final Yaml yaml = new Yaml();
                    Map<String, Object> properties = yaml.loadAs(Files.newInputStream(yml), Map.class);
                    return YamlExtractor.extract(properties);
                }
                return null;
            }
        }
    }

    private static class YamlExtractor {

        static DatabaseConfiguration extract(Map<String, Object> properties) {
            String username = "";
            String password = "";
            String url = "";

            if (properties.containsKey("spring")) {
                final Map<String, Object> spring = (Map<String, Object>) properties.get("spring");
                if (spring.containsKey("datasource")) {
                    final Map<String, Object> datasource = (Map<String, Object>) spring.get("datasource");

                    if (datasource.containsKey("username")) {
                        username = (String) datasource.get("username");
                    }

                    if (datasource.containsKey("password")) {
                        password = (String) datasource.get("password");
                    }

                    if (datasource.containsKey("url")) {
                        url = (String) datasource.get("url");
                    }
                }
            }

            if (username.isEmpty() && password.isEmpty() && url.isEmpty()) {
                return null;
            }

            return new DatabaseConfiguration(username, password, JdbcUrlParser.getDatabase(url));
        }

    }

    private static class PropertiesExtractor {

        private static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
        private static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
        private static final String SPRING_DATASOURCE_URL = "spring.datasource.url";

        static DatabaseConfiguration extract(Properties properties) {
            String username = "";
            String password = "";
            String url = "";

            if (properties.containsKey(SPRING_DATASOURCE_USERNAME)) {
                username = properties.getProperty(SPRING_DATASOURCE_USERNAME);
            }

            if (properties.containsKey(SPRING_DATASOURCE_PASSWORD)) {
                password = properties.getProperty(SPRING_DATASOURCE_PASSWORD);
            }

            if (properties.containsKey(SPRING_DATASOURCE_URL)) {
                url = properties.getProperty(SPRING_DATASOURCE_URL);
            }

            if (username.isEmpty() && password.isEmpty() && url.isEmpty()) {
                return null;
            }

            return new DatabaseConfiguration(username, password, JdbcUrlParser.getDatabase(url));
        }
    }

}
