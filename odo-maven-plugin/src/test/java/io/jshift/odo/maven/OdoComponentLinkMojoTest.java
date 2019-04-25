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
public class OdoComponentLinkMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehaviorCurrentComponentToAnotherComponent(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoComponentLinkMojo odoComponentLinkMojo = new OdoComponentLinkMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> componentLinkConfiguration = new HashMap<>();
        componentLinkConfiguration.put("app", "myapp");
        componentLinkConfiguration.put("port", "8080");
        componentLinkConfiguration.put("project", "myproject");
        componentLinkConfiguration.put("waitForTarget", "true");
        componentLinkConfiguration.put("name",  "target");

        odoComponentLinkMojo.linkComponent = componentLinkConfiguration;
        odoComponentLinkMojo.project = project;
        odoComponentLinkMojo.odo = odo;

        // When:
        odoComponentLinkMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo component link target --app myapp --project myproject --port 8080 --wait-for-target");
    }

    @Test
    public void testMojoBehaviorlinkComponentAToComponentB(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoComponentLinkMojo odoComponentLinkMojo = new OdoComponentLinkMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> componentLinkConfiguration = new HashMap<>();
        componentLinkConfiguration.put("waitForTarget", "true");
        componentLinkConfiguration.put("wait", "true");
        componentLinkConfiguration.put("component", "ComponentA");
        componentLinkConfiguration.put("name",  "ComponentB");

        odoComponentLinkMojo.linkComponent = componentLinkConfiguration;
        odoComponentLinkMojo.project = project;

        odoComponentLinkMojo.odo = odo;

        // When:
        odoComponentLinkMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo component link ComponentB --component ComponentA --wait --wait-for-target");
    }
}