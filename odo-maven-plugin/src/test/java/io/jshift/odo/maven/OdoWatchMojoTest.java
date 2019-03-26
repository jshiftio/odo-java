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
public class OdoWatchMojoTest {

    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoWatchMojo odoWatchMojo = new OdoWatchMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> watchConfig = new HashMap<>();
        watchConfig.put("project", "myproject");
        watchConfig.put("app", "myapp");
        watchConfig.put("ignore", "foob*, badregex*");
        watchConfig.put("delay", "1000");
        watchConfig.put("componentName", "test-component");

        odoWatchMojo.watch = watchConfig;
        odoWatchMojo.odo = odo;
        odoWatchMojo.project = project;

        // When:
        odoWatchMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo watch test-component --app myapp --project myproject --delay 1000 --ignore foob*, badregex*");
    }

    @Test
    public void testMojoBehaviorSingleItemListIgnore(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoWatchMojo odoWatchMojo = new OdoWatchMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> watchConfig = new HashMap<>();
        watchConfig.put("project", "myproject");
        watchConfig.put("app", "myapp");
        watchConfig.put("ignore", "foob*");
        watchConfig.put("delay", "1000");
        watchConfig.put("componentName", "test-component");

        odoWatchMojo.project = project;
        odoWatchMojo.watch = watchConfig;
        odoWatchMojo.odo = odo;

        // When:
        odoWatchMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo watch test-component --app myapp --project myproject --delay 1000 --ignore foob*");
    }

    @Test
    public void testMojoBehaviorMinimal(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoWatchMojo odoWatchMojo = new OdoWatchMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        odoWatchMojo.odo = odo;
        odoWatchMojo.project = project;

        // When:
        odoWatchMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo watch");
    }

    @Test
    public void testMojoBehaviorWithDelay(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoWatchMojo odoWatchMojo = new OdoWatchMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));

        Map<String, String> watchConfig = new HashMap<>();
        watchConfig.put("delay", "1000");

        odoWatchMojo.project = project;
        odoWatchMojo.odo = odo;
        odoWatchMojo.watch = watchConfig;

        // When:
        odoWatchMojo.execute();

        // Then:gi
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo watch --delay 1000");
    }

}
