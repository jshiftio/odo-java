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
public class OdoComponentPushMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoComponentPushMojo odoComponentPushMojo = new OdoComponentPushMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> componentPushConfiguration = new HashMap<>();
        componentPushConfiguration.put("app", "myapp");
        componentPushConfiguration.put("local", "~/myCode");

        odoComponentPushMojo.artifactId = "fooproject";
        odoComponentPushMojo.project = project;
        odoComponentPushMojo.odo = odo;
        odoComponentPushMojo.pushComponent = componentPushConfiguration;

        // When:
        odoComponentPushMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo component push fooproject --app myapp --local ~/myCode --project /tmp/foodir");
    }
}
