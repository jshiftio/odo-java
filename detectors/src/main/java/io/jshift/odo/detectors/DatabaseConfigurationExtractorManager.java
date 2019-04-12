package io.jshift.odo.detectors;

import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.service.DatabaseConfiguration;
import io.jshift.odo.detectors.spi.DatabaseConfigurationExtractor;
import java.util.List;
import java.util.Optional;

public class DatabaseConfigurationExtractorManager {

    private DatabaseConfigurationExtractorLoader databaseConfigurationExtractorLoader;

    public DatabaseConfigurationExtractorManager(DatabaseConfigurationExtractorLoader databaseConfigurationExtractorLoader) {
        this.databaseConfigurationExtractorLoader = databaseConfigurationExtractorLoader;
    }

    public Optional<DatabaseConfiguration> getDatabaseConfiguration(Extractor extractor) {
        final List<DatabaseConfigurationExtractor> detectors = this.databaseConfigurationExtractorLoader.databaseConfigurationExtractors(extractor);

        for (DatabaseConfigurationExtractor detector : detectors) {
            final Optional<DatabaseConfiguration> extract = detector.extract();

            if (extract.isPresent()) {
                return extract;
            }

        }

        return Optional.empty();
    }

}
