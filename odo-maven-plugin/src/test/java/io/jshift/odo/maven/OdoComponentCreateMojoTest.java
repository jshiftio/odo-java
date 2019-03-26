package io.jshift.odo.maven;

import io.jshift.odo.assertj.OdoExecutorAssertion;
import io.jshift.odo.core.Odo;
import io.jshift.odo.junit5.OdoExecutorStubInjector;
import io.jshift.odo.odo.OdoExecutorStub;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo component create openjdk18 fooproject --local /tmp/foodir --max-cpu 2 --max-memory 2 --port 8080, 8000, 8001");
    }
}