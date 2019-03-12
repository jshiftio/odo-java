package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OdoExecutorStubInjector.class})
public class OdoComponentCreateMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoComponentCreateMojo odoComponentCreateMojo = new OdoComponentCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> componentCreateConfiguration = new HashMap<>();
        componentCreateConfiguration.put("maxMemory", "2");
        componentCreateConfiguration.put("maxCpu", "2");
        componentCreateConfiguration.put("port", "8080,8000,8001");
        String name = "fooproject";

        odoComponentCreateMojo.createComponent = componentCreateConfiguration;
        odoComponentCreateMojo.componentName = name;
        odoComponentCreateMojo.project = project;
        odoComponentCreateMojo.componentType = "openjdk18";

        odoComponentCreateMojo.odo = odo;

        // When:
        odoComponentCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo component create openjdk18 fooproject --local /tmp/foodir --max-cpu 2 --max-memory 2 --port 8080, 8000, 8001");
    }
}