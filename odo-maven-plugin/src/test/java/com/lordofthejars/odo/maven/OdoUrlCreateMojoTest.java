package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OdoExecutorStubInjector.class})
public class OdoUrlCreateMojoTest {

    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoUrlCreateMojo odoUrlCreateMojo = new OdoUrlCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> createUrlConfig = new HashMap<>();
        createUrlConfig.put("port", "8080");
        createUrlConfig.put("component", "test-component");
        createUrlConfig.put("urlName", "test-url");

        odoUrlCreateMojo.createUrl = createUrlConfig;
        odoUrlCreateMojo.odo = odo;
        odoUrlCreateMojo.project = project;

        // When:
        odoUrlCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo url create test-url --component test-component --port 8080");
    }

    @Test
    public void testMojoBehaviorOnlyPort(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoUrlCreateMojo odoUrlCreateMojo = new OdoUrlCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> createUrlConfig = new HashMap<>();
        createUrlConfig.put("port", "8080");

        odoUrlCreateMojo.createUrl = createUrlConfig;
        odoUrlCreateMojo.odo = odo;
        odoUrlCreateMojo.project = project;

        // When:
        odoUrlCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo url create --port 8080");
    }
}
