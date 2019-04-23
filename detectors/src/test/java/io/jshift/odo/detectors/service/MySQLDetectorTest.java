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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith({OdoExecutorStubInjector.class, MockitoExtension.class})
public class MySQLDetectorTest {

    @Mock
    Extractor extractor;

    @Mock
    DatabaseConfigurationExtractorManager databaseConfigurationExtractorManager;

    @Test
    public void should_apply_odo_commands_to_mysql(OdoExecutorStub odoExecutorStub) {

        // Given

        final Set<Dependency> dependencySet = new HashSet<>();
        dependencySet.add(MySQLDetector.MYSQL_DEPENDENCY);
        Mockito.when(extractor.extractDependencies()).thenReturn(dependencySet);
        Mockito.when(extractor.workingDirectory()).thenReturn(Paths.get("/tmp"));

        final Odo odo = new Odo(odoExecutorStub);
        final MySQLDetector mySQLDetector = new MySQLDetector();
        mySQLDetector.configure(extractor, odo);

        // When

        mySQLDetector.detect();
        mySQLDetector.apply();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub)
            .hasExecuted("odo service create mysql-persistent --plan default --wait");
    }

    @Test
    public void should_apply_odo_commands_to_mysql_with_configuration(OdoExecutorStub odoExecutorStub) {

        // Given

        when(databaseConfigurationExtractorManager.getDatabaseConfiguration(extractor))
            .thenReturn(Optional.of(new DatabaseConfiguration("sa", "sa", "mydatabase")));

        final Set<Dependency> dependencySet = new HashSet<>();
        dependencySet.add(MySQLDetector.MYSQL_DEPENDENCY);
        Mockito.when(extractor.extractDependencies()).thenReturn(dependencySet);
        Mockito.when(extractor.workingDirectory()).thenReturn(Paths.get("/tmp"));

        final Odo odo = new Odo(odoExecutorStub);
        final MySQLDetector mySQLDetector = new MySQLDetector();
        mySQLDetector.configure(extractor, odo);
        mySQLDetector.databaseConfigurationExtractorManager = databaseConfigurationExtractorManager;

        // When

        mySQLDetector.detect();
        mySQLDetector.apply();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub)
            .hasExecuted("odo service create mysql-persistent --plan default --wait -p mysql_user=sa -p mysql_password=sa -p mysql_database=mydatabase");
    }

}
