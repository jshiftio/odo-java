package com.lordofthejars.odo.detectors.service;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.detectors.extractor.Dependency;
import com.lordofthejars.odo.detectors.extractor.Extractor;
import com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({OdoExecutorStubInjector.class, MockitoExtension.class})
public class PostgreSQLDetectorTest {

    @Mock
    Extractor extractor;

    @Test
    public void should_apply_odo_commands_to_postgresql(OdoExecutorStub odoExecutorStub) {

        // Given

        final Set<Dependency> dependencySet = new HashSet<>();
        dependencySet.add(PostgreSQLDetector.POSTGRESQL_DEPENDENCY);
        Mockito.when(extractor.extractDependencies()).thenReturn(dependencySet);
        Mockito.when(extractor.workingDirectory()).thenReturn(Paths.get("/tmp"));

        final Odo odo = new Odo(odoExecutorStub);
        final PostgreSQLDetector postgreSQLDetector = new PostgreSQLDetector();
        postgreSQLDetector.configure(extractor, odo);

        // When

        postgreSQLDetector.detect();
        postgreSQLDetector.apply();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub)
            .hasExecuted("odo service create postgresql-persistent --plan dev --wait");
    }

}
