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
public class JavaExecDetectorTest {

    @Mock
    Extractor extractor;

    @Test
    public void should_detect_java_exec(OdoExecutorStub odoExecutorStub) {

        // Given

        Mockito.when(extractor.extractTypeOfProject()).thenReturn(Packaging.JAR);
        Mockito.when(extractor.extractArtifactId()).thenReturn("demo");

        final Odo odo = new Odo(odoExecutorStub);
        final JavaExecDetector javaExecDetector = new JavaExecDetector();
        javaExecDetector.configure(extractor, odo);

        // When

        final String name = javaExecDetector.apply();
        final boolean detect = javaExecDetector.detect();

        // Then

        Assertions.assertThat(name).isEqualTo("demo");
        Assertions.assertThat(detect).isTrue();
    }

    @Test
    public void should_apply_odo_commands_to_java_exec(OdoExecutorStub odoExecutorStub) {

        // Given

        Mockito.when(extractor.extractTypeOfProject()).thenReturn(Packaging.JAR);
        Mockito.when(extractor.extractArtifactId()).thenReturn("demo");

        final Odo odo = new Odo(odoExecutorStub);
        final JavaExecDetector javaExecDetector = new JavaExecDetector();
        javaExecDetector.configure(extractor, odo);

        // When

        javaExecDetector.detect();
        final String name = javaExecDetector.apply();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub)
            .hasExecuted("odo component create openjdk18 " + name + " --wait", "odo component push", "odo url create --component demo --port 8080");
    }

}
