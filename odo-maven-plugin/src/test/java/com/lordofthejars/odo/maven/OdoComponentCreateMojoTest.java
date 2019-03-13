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
public class OdoComponentCreateMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoComponentCreateMojo odoComponentCreateMojo = new OdoComponentCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> componentCreateConfiguration = new HashMap<>();
        componentCreateConfiguration.put("maxMemory", "2");
        componentCreateConfiguration.put("maxCpu", "2");
        componentCreateConfiguration.put("port", "8080,8000,8001");
        componentCreateConfiguration.put("componentName" , "fooproject");
        componentCreateConfiguration.put("componentType", "openjdk18");
        componentCreateConfiguration.put("local", "/tmp/foodir");

        odoComponentCreateMojo.createComponent = componentCreateConfiguration;
        odoComponentCreateMojo.project = project;

        odoComponentCreateMojo.odo = odo;

        // When:
        odoComponentCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo component create openjdk18 fooproject --local /tmp/foodir --max-cpu 2 --max-memory 2 --port 8080, 8000, 8001");
    }
}