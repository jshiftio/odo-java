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

import java.util.HashMap;
import java.util.Map;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;

@ExtendWith({MockitoExtension.class, OdoExecutorStubInjector.class})
public class OdoComponentDeleteMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoComponentDeleteMojo odoComponentDeleteMojo = new OdoComponentDeleteMojo();
        Odo odo = new Odo(odoExecutorStub);

        Map<String, String> componentDeleteConfiguration = new HashMap<>();
        componentDeleteConfiguration.put("app", "fooproject");

        odoComponentDeleteMojo.deleteComponent = componentDeleteConfiguration;
        odoComponentDeleteMojo.project = project;
        odoComponentDeleteMojo.componentType = "openjdk18";

        odoComponentDeleteMojo.odo = odo;

        // When:
        odoComponentDeleteMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo component delete openjdk18 --app fooproject");
    }
}
