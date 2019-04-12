package io.jshift.odo.detectors.spi;

import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.service.DatabaseConfiguration;
import java.util.Optional;

public interface DatabaseConfigurationExtractor {

    Optional<DatabaseConfiguration> extract();
    void setExtractor(Extractor extractor);

}
