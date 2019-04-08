package io.jshift.odo.detectors.service;

import io.jshift.odo.detectors.BaseDetector;
import io.jshift.odo.detectors.DatabaseConfigurationExtractorLoader;
import io.jshift.odo.detectors.DatabaseConfigurationExtractorManager;
import io.jshift.odo.detectors.extractor.Dependency;
import io.jshift.odo.detectors.spi.DatabaseConfigurationExtractor;
import io.jshift.odo.detectors.util.DetectorType;
import java.util.Optional;

public abstract class ServiceDetector extends BaseDetector {

    protected DatabaseConfigurationExtractorManager databaseConfigurationExtractorManager;

    protected ServiceDetector() {
        super();
        this.databaseConfigurationExtractorManager = new DatabaseConfigurationExtractorManager(new DatabaseConfigurationExtractorLoader());
    }

    @Override
    public DetectorType getType() {
        return DetectorType.SERVICE;
    }

    protected Optional<DatabaseConfiguration> getDatabaseConfigurationExtractor() {
        return this.databaseConfigurationExtractorManager.getDatabaseConfiguration(extractor);
    }

    protected boolean isDependencyRegistered(Dependency dependency) {
        return extractor.extractDependencies().contains(dependency);
    }

}