package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OdoExecutorStubInjector.class})
public class OdoComponentUnlinkMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehaviorCurrentComponentToAnotherComponent(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoComponentUnlinkMojo odoComponentUnlinkMojo = new OdoComponentUnlinkMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> componentUnlinkConfiguration = new HashMap<>();
        componentUnlinkConfiguration.put("app", "myapp");
        componentUnlinkConfiguration.put("port", "8080");
        componentUnlinkConfiguration.put("project", "myproject");
        componentUnlinkConfiguration.put("name", "mysql-service");

        odoComponentUnlinkMojo.unlinkComponent = componentUnlinkConfiguration;
        odoComponentUnlinkMojo.project = project;

        odoComponentUnlinkMojo.odo = odo;

        // When:
        odoComponentUnlinkMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo component unlink mysql-service --app myapp --port 8080 --project myproject");
    }

}
