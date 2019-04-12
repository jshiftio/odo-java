package io.jshift.odo.detectors;

import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.spi.DatabaseConfigurationExtractor;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DatbaseConfigurationExtractorLoaderTest {

    @Mock
    Extractor extractor;

    @Test
    public void should_load_load_extractors_from_classpath() {

        // Given

        final DatabaseConfigurationExtractorLoader databaseConfigurationExtractorLoader = new DatabaseConfigurationExtractorLoader();

        // When

        final List<DatabaseConfigurationExtractor> detectors = databaseConfigurationExtractorLoader.databaseConfigurationExtractors(extractor);

        // Then

        assertThat(detectors)
            .isNotEmpty();
    }

}
