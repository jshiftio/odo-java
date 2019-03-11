package com.lordofthejars.odo.detectors;

import com.lordofthejars.odo.core.Odo;
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
public class DetectorHubTest {

    @Mock
    Extractor extractor;

    @Test
    public void should_link_component_and_services(OdoExecutorStub odoExecutorStub) {

        // Given

        final Set<Dependency> dependencySet = new HashSet<>();
        dependencySet.add(new Dependency("mysql", "mysql-connector-java"));
        Mockito.when(extractor.extractDependencies()).thenReturn(dependencySet);
        Mockito.when(extractor.workingDirectory()).thenReturn(Paths.get("/tmp"));
        Mockito.when(extractor.extractTypeOfProject()).thenReturn(Packaging.JAR);
        Mockito.when(extractor.extractArtifactId()).thenReturn("demo");

        final Odo odo = new Odo(odoExecutorStub);
        final DetectorHub detectorHub = new DetectorHub(extractor, odo);

        // When

        detectorHub.execute();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub)
            .hasExecuted("odo service create mysql-persistent --plan dev --wait",
            "odo component create openjdk18 demo --wait",
            "odo component push",
            "odo component link mysql-persistent --component demo --port 8080 --wait",
            "odo url create --component demo");
    }
}
