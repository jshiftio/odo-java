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
        componentPushConfiguration.put("project", "myproject");
        componentPushConfiguration.put("componentName", "fooproject");

        odoComponentPushMojo.project = project;
        odoComponentPushMojo.odo = odo;
        odoComponentPushMojo.pushComponent = componentPushConfiguration;
        // When:
        odoComponentPushMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo component push fooproject --app myapp --project myproject --local ~/myCode");
    }

    @Test
    public void testMojoBehaviorWithCurrentComponent(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoComponentPushMojo odoComponentPushMojo = new OdoComponentPushMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));

        odoComponentPushMojo.project = project;
        odoComponentPushMojo.odo = odo;
        // When:
        odoComponentPushMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo component push");
    }
}
