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
public class QuarkusDatabaseExtractorTest {

    @Mock
    Extractor extractor;

    @BeforeEach
    public void setMocks() {
        final Set<Dependency> dependencies = new HashSet<>();
        dependencies.add(QuarkusDatabaseExtractor.QUARKUS_DEPENDENCY);
        when(extractor.extractDependencies()).thenReturn(dependencies);
    }

    @Test
    public void should_read_configuration_from_file() {

        // Given

        final QuarkusDatabaseExtractor quarkusDatabaseExtractor = new QuarkusDatabaseExtractor();
        quarkusDatabaseExtractor.setExtractor(extractor);
        quarkusDatabaseExtractor.application = Paths.get("src/test/resources", "application-quarkus.properties");

        // When

        final Optional<DatabaseConfiguration> databaseConfiguration = quarkusDatabaseExtractor.extract();

        // Then

        assertThat(databaseConfiguration)
            .isNotEmpty()
            .get()
            .isEqualTo(new DatabaseConfiguration("sarah", "connor", "mydatabase"));

    }

    @Test
    public void should_not_load_any_configuration_if_not_exists() {

        // Given

        final QuarkusDatabaseExtractor quarkusDatabaseExtractor = new QuarkusDatabaseExtractor();
        quarkusDatabaseExtractor.setExtractor(extractor);
        quarkusDatabaseExtractor.application = Paths.get("src/test/resources", "application-quarkus-not-exists.properties");

        // When

        final Optional<DatabaseConfiguration> databaseConfiguration = quarkusDatabaseExtractor.extract();

        // Then

        assertThat(databaseConfiguration)
            .isEmpty();

    }

}
