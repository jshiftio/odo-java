package io.jshift.odo.detectors.service;

import io.jshift.odo.assertj.OdoExecutorAssertion;
import io.jshift.odo.core.Odo;
import io.jshift.odo.detectors.DatabaseConfigurationExtractorManager;
import io.jshift.odo.junit5.OdoExecutorStubInjector;
import io.jshift.odo.detectors.extractor.Dependency;
import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.odo.OdoExecutorStub;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith({OdoExecutorStubInjector.class, MockitoExtension.class})
public class PostgreSQLDetectorTest {

    @Mock
    Extractor extractor;

    @Mock
    DatabaseConfigurationExtractorManager databaseConfigurationExtractorManager;

    @Test
    public void should_apply_odo_commands_to_postgresql(OdoExecutorStub odoExecutorStub) {

        // Given

        final Set<Dependency> dependencySet = new HashSet<>();
        dependencySet.add(PostgreSQLDetector.POSTGRESQL_DEPENDENCY);
        when(extractor.extractDependencies()).thenReturn(dependencySet);
        when(extractor.workingDirectory()).thenReturn(Paths.get("/tmp"));

        final Odo odo = new Odo(odoExecutorStub);
        final PostgreSQLDetector postgreSQLDetector = new PostgreSQLDetector();
        postgreSQLDetector.configure(extractor, odo);

        // When

        postgreSQLDetector.detect();
        postgreSQLDetector.apply();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub)
            .hasExecuted("odo service create postgresql-persistent --plan default --wait");
    }

    @Test
    public void should_apply_odo_commands_to_quarkus_postgres(OdoExecutorStub odoExecutorStub) {
        // Given

        final Set<Dependency> dependencySet = new HashSet<>();
        dependencySet.add(PostgreSQLDetector.POSTGRESQL_QUARKUS_DEPENDENCY);
        when(extractor.extractDependencies()).thenReturn(dependencySet);
        when(extractor.workingDirectory()).thenReturn(Paths.get("/tmp"));

        final Odo odo = new Odo(odoExecutorStub);
        final PostgreSQLDetector postgreSQLDetector = new PostgreSQLDetector();
        postgreSQLDetector.configure(extractor, odo);

        // When

        postgreSQLDetector.detect();
        postgreSQLDetector.apply();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub)
            .hasExecuted("odo service create postgresql-persistent --plan default --wait");
    }

    @Test
    public void should_apply_odo_commands_to_postgresql_with_configuration(OdoExecutorStub odoExecutorStub) {

        // Given

        when(databaseConfigurationExtractorManager.getDatabaseConfiguration(extractor))
            .thenReturn(Optional.of(new DatabaseConfiguration("sa", "sa", "mydatabase")));

        final Set<Dependency> dependencySet = new HashSet<>();
        dependencySet.add(PostgreSQLDetector.POSTGRESQL_DEPENDENCY);
        when(extractor.extractDependencies()).thenReturn(dependencySet);
        when(extractor.workingDirectory()).thenReturn(Paths.get("/tmp"));

        final Odo odo = new Odo(odoExecutorStub);
        final PostgreSQLDetector postgreSQLDetector = new PostgreSQLDetector();
        postgreSQLDetector.configure(extractor, odo);
        postgreSQLDetector.databaseConfigurationExtractorManager = databaseConfigurationExtractorManager;

        // When

        postgreSQLDetector.detect();
        postgreSQLDetector.apply();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub)
            .hasExecuted("odo service create postgresql-persistent --plan default --wait -p postgresql_user=sa -p postgresql_password=sa -p postgresql_url=mydatabase");

    }
}
