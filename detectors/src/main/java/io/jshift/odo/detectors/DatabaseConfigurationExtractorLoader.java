package io.jshift.odo.detectors;

import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.spi.DatabaseConfigurationExtractor;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DatabaseConfigurationExtractorLoader {


    private static ServiceLoader<DatabaseConfigurationExtractor> loader = ServiceLoader.load(DatabaseConfigurationExtractor.class);

    private List<DatabaseConfigurationExtractor> databaseConfigurationExtractors = new ArrayList<>();

    public List<DatabaseConfigurationExtractor> detectors(Extractor extractor) {
        if (databaseConfigurationExtractors.isEmpty()) {
            databaseConfigurationExtractors.addAll(
                StreamSupport.stream(loader.spliterator(), false)
                    .peek(databaseConfigurationExtractor -> databaseConfigurationExtractor.setExtractor(extractor))
                    .collect(Collectors.toList())
            );
        }

        return databaseConfigurationExtractors;
    }

}
