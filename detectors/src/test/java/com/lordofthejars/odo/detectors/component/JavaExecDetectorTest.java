package com.lordofthejars.odo.detectors.component;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.detectors.extractor.Extractor;
import com.lordofthejars.odo.detectors.util.Packaging;
import com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
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
