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

        when(project.getArtifactId()).thenReturn("myartifact");
        Map<String, String> componentUnlinkConfiguration = new HashMap<>();
        componentUnlinkConfiguration.put("app", "myapp");
        componentUnlinkConfiguration.put("component", "mycomponent");
        componentUnlinkConfiguration.put("port", "8080");
        componentUnlinkConfiguration.put("project", "myproject");

        odoComponentUnlinkMojo.unlinkComponent = componentUnlinkConfiguration;
        odoComponentUnlinkMojo.project = project;

        odoComponentUnlinkMojo.odo = odo;

        // When:
        odoComponentUnlinkMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo component unlink myartifact --component mycomponent --app myapp --port 8080 --project myproject");
    }

    @Test
    public void testMojoBehaviorlinkComponentAToComponentB(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoComponentLinkMojo odoComponentLinkMojo = new OdoComponentLinkMojo();
        Odo odo = new Odo(odoExecutorStub);

        Map<String, String> componentLinkConfiguration = new HashMap<>();
        componentLinkConfiguration.put("component", "component2");

        odoComponentLinkMojo.linkComponent = componentLinkConfiguration;
        odoComponentLinkMojo.project = project;
        odoComponentLinkMojo.component = "component1";

        odoComponentLinkMojo.odo = odo;

        // When:
        odoComponentLinkMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo component link component1 --component component2");
    }
}
