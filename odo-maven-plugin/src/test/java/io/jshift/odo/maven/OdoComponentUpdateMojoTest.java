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
public class OdoComponentUpdateMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoComponentUpdateMojo odoComponentUpdateMojo = new OdoComponentUpdateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));

        odoComponentUpdateMojo.project = project;
        odoComponentUpdateMojo.odo = odo;

        // When:
        odoComponentUpdateMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo component update");
    }

    @Test
    public void testMojoBehaviorWithLocalPath(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoComponentUpdateMojo odoComponentUpdateMojo = new OdoComponentUpdateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> componentUpdateConfiguration = new HashMap<>();
        componentUpdateConfiguration.put("project", "myproject");
        componentUpdateConfiguration.put("app", "myapp");
        componentUpdateConfiguration.put("componentName", "test-component");
        componentUpdateConfiguration.put("local", "/tmp/foodir/path/foobar");

        odoComponentUpdateMojo.updateComponent = componentUpdateConfiguration;
        odoComponentUpdateMojo.project = project;

        odoComponentUpdateMojo.odo = odo;

        // When:
        odoComponentUpdateMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo component update test-component --project myproject --app myapp --local /tmp/foodir/path/foobar");
    }

    @Test
    public void testMojoBehaviorWithGit(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoComponentUpdateMojo odoComponentUpdateMojo = new OdoComponentUpdateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> componentUpdateConfiguration = new HashMap<>();
        componentUpdateConfiguration.put("app", "myapp");
        componentUpdateConfiguration.put("project", "myproject");
        componentUpdateConfiguration.put("git", "http://abc.xyz/repo.git");

        odoComponentUpdateMojo.updateComponent = componentUpdateConfiguration;
        odoComponentUpdateMojo.project = project;
        odoComponentUpdateMojo.odo = odo;

        // When:
        odoComponentUpdateMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo component update --project myproject --app myapp --git http://abc.xyz/repo.git");
    }

    @Test
    public void testMojoBehaviorWithBinary(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoComponentUpdateMojo odoComponentUpdateMojo = new OdoComponentUpdateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> componentUpdateConfiguration = new HashMap<>();
        componentUpdateConfiguration.put("project", "myproject");
        componentUpdateConfiguration.put("app", "myapp");
        componentUpdateConfiguration.put("componentName", "test-component");
        componentUpdateConfiguration.put("binary", "/tmp/foodir/path/foobar.tar.gz");

        odoComponentUpdateMojo.updateComponent = componentUpdateConfiguration;
        odoComponentUpdateMojo.project = project;
        odoComponentUpdateMojo.odo = odo;

        // When:
        odoComponentUpdateMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo component update test-component --project myproject --app myapp --binary /tmp/foodir/path/foobar.tar.gz");
    }
}
