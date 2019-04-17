package io.jshift.odo.detectors.service;

import java.nio.file.Paths;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaDatabaseExtractorTest {

    @Test
    public void should_load_datbase_configuration_from_file() {

        // Given

        final JpaDatabaseExtractor jpaDatabaseExtractor = new JpaDatabaseExtractor();
        jpaDatabaseExtractor.persistence = Paths.get("src/test/resources", "persistence.xml");

        // When

        final Optional<DatabaseConfiguration> databaseConfiguration = jpaDatabaseExtractor.extract();

        // Then

        assertThat(databaseConfiguration)
            .isNotEmpty()
            .get()
            .isEqualTo(new DatabaseConfiguration("APP", "APP", "test"));

    }

}
