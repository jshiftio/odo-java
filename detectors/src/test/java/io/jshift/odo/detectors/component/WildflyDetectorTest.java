package io.jshift.odo.detectors.component;

import io.jshift.odo.assertj.OdoExecutorAssertion;
import io.jshift.odo.core.Odo;
import io.jshift.odo.junit5.OdoExecutorStubInjector;
import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.util.Packaging;
import io.jshift.odo.odo.OdoExecutorStub;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({OdoExecutorStubInjector.class, MockitoExtension.class})
public class WildflyDetectorTest {

    @Mock
    Extractor extractor;

    @Test
    public void should_detect_wildfly(OdoExecutorStub odoExecutorStub) {

        // Given

        Mockito.when(extractor.extractTypeOfProject()).thenReturn(Packaging.WAR);
        Mockito.when(extractor.extractArtifactId()).thenReturn("demo");

        final Odo odo = new Odo(odoExecutorStub);
        final WildflyDetector wildflyDetector = new WildflyDetector();
        wildflyDetector.configure(extractor, odo);

        // When

        final String name = wildflyDetector.apply();
        final boolean detect = wildflyDetector.detect();

        // Then

        Assertions.assertThat(name).isEqualTo("demo");
        Assertions.assertThat(detect).isTrue();
    }

    @Test
    public void should_apply_odo_commands_to_wildfly(OdoExecutorStub odoExecutorStub) {

        // Given

        Mockito.when(extractor.extractTypeOfProject()).thenReturn(Packaging.WAR);
        Mockito.when(extractor.extractArtifactId()).thenReturn("demo");

        final Odo odo = new Odo(odoExecutorStub);
        final WildflyDetector wildflyDetector = new WildflyDetector();
        wildflyDetector.configure(extractor, odo);

        // When

        wildflyDetector.detect();
        final String name = wildflyDetector.apply();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub)
            .hasExecuted("odo component create wildfly " + name + " --wait", "odo component push", "odo url create --component demo --port 8080");
    }

}
