package io.jshift.odo.detectors;

import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.service.DatabaseConfiguration;
import io.jshift.odo.detectors.spi.DatabaseConfigurationExtractor;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DatabaseConfigurationExtractorManagerTest {

    @Mock
    Extractor extractor;

    @Mock
    DatabaseConfigurationExtractor databaseConfigurationExtractor;

    @Mock
    DatabaseConfigurationExtractorLoader databaseConfigurationExtractorLoader;

    @BeforeEach
    public void setupLoader() {
        when(databaseConfigurationExtractorLoader.databaseConfigurationExtractors(extractor)).thenReturn(Arrays.asList(databaseConfigurationExtractor));
    }

    @Test
    public void should_return_configured_database_configuration_if_meets_criteria() {

        // Given

        when(databaseConfigurationExtractor.extract()).thenReturn(Optional.of(new DatabaseConfiguration("SA", "SA", "mydatabase")));
        final DatabaseConfigurationExtractorManager databaseConfigurationExtractorManager = new DatabaseConfigurationExtractorManager(databaseConfigurationExtractorLoader);

        // When

        final Optional<DatabaseConfiguration> databaseConfiguration =
            databaseConfigurationExtractorManager.getDatabaseConfiguration(extractor);

        // Then

        assertThat(databaseConfiguration).isNotEmpty();

    }

    @Test
    public void should_return_empty_configuration_if_criteria_no_meet() {

        // Given

        when(databaseConfigurationExtractor.extract()).thenReturn(Optional.empty());
        final DatabaseConfigurationExtractorManager databaseConfigurationExtractorManager = new DatabaseConfigurationExtractorManager(databaseConfigurationExtractorLoader);

        // When

        final Optional<DatabaseConfiguration> databaseConfiguration =
            databaseConfigurationExtractorManager.getDatabaseConfiguration(extractor);

        // Then

        assertThat(databaseConfiguration).isEmpty();

    }

}
