package io.jshift.odo.detectors.service;

import io.jshift.odo.detectors.extractor.Dependency;
import io.jshift.odo.detectors.extractor.Extractor;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpringBootDatabaseExtractorTest {

    @Mock
    Extractor extractor;

    @BeforeEach
    public void setMocks() {
        final Set<Dependency> dependencies = new HashSet<>();
        dependencies.add(SpringBootDatabaseExtractor.SPRING_BOOT_JPA_DEPENDENCY);
        when(extractor.extractDependencies()).thenReturn(dependencies);
    }

    @Test
    public void should_read_configuration_from_properties_file() {

        // Given

        SpringBootDatabaseExtractor springBootDatabaseExtractor = new SpringBootDatabaseExtractor();
        springBootDatabaseExtractor.setExtractor(extractor);
        springBootDatabaseExtractor.properties = Paths.get("src/test/resources", "spring-data.properties");

        // When

        final Optional<DatabaseConfiguration> databaseConfiguration = springBootDatabaseExtractor.extract();

        // Then

        assertThat(databaseConfiguration)
            .isNotEmpty()
            .get()
            .isEqualTo(new DatabaseConfiguration("sa", "sa"));

    }

    @Test
    public void should_read_configuration_form_yaml_file() {

        // Given

        SpringBootDatabaseExtractor springBootDatabaseExtractor = new SpringBootDatabaseExtractor();
        springBootDatabaseExtractor.setExtractor(extractor);
        springBootDatabaseExtractor.properties = Paths.get("src/test/resources", "spring-data-not-existing.properties");
        springBootDatabaseExtractor.yaml = Paths.get("src/test/resources", "spring-data.yaml");

        // When

        final Optional<DatabaseConfiguration> databaseConfiguration = springBootDatabaseExtractor.extract();

        // Then

        assertThat(databaseConfiguration)
            .isNotEmpty()
            .get()
            .isEqualTo(new DatabaseConfiguration("as", "as"));

    }

    @Test
    public void should_not_load_any_configuration_if_not_exists() {

        // Given

        SpringBootDatabaseExtractor springBootDatabaseExtractor = new SpringBootDatabaseExtractor();
        springBootDatabaseExtractor.setExtractor(extractor);
        springBootDatabaseExtractor.properties = Paths.get("src/test/resources", "spring-data-not-existing.properties");
        springBootDatabaseExtractor.yaml = Paths.get("src/test/resources", "spring-data-not-existing.yaml");

        // When

        final Optional<DatabaseConfiguration> databaseConfiguration = springBootDatabaseExtractor.extract();

        // Then

        assertThat(databaseConfiguration)
            .isEmpty();

    }

}
