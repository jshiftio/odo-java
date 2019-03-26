package com.lordofthejars.odo.detectors.service;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.detectors.component.JavaExecDetector;
import com.lordofthejars.odo.detectors.extractor.Dependency;
import com.lordofthejars.odo.detectors.extractor.Extractor;
import com.lordofthejars.odo.detectors.util.Packaging;
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
public class MySQLDetectorTest {

    @Mock
    Extractor extractor;

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

}
