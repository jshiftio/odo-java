package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;

@ExtendWith({OdoExecutorStubInjector.class})
public class OdoWatchMojoTest {
    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoWatchMojo odoWatchMojo = new OdoWatchMojo();
        Odo odo = new Odo(odoExecutorStub);

        Map<String, String> watchConfig = new HashMap<>();
        watchConfig.put("project", "myproject");
        watchConfig.put("app", "myapp");
        watchConfig.put("ignore", "foob*, badregex*");
        watchConfig.put("delay", "1000");

        String artifactId = "fooproject";

        odoWatchMojo.componentName = artifactId;
        odoWatchMojo.watch = watchConfig;
        odoWatchMojo.odo = odo;

        // When:
        odoWatchMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo watch fooproject --app myapp --project myproject --delay 1000 --ignore foob*, badregex*");
    }

    @Test
    public void testMojoBehaviorSingleItemListIgnore(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoWatchMojo odoWatchMojo = new OdoWatchMojo();
        Odo odo = new Odo(odoExecutorStub);

        Map<String, String> watchConfig = new HashMap<>();
        watchConfig.put("project", "myproject");
        watchConfig.put("app", "myapp");
        watchConfig.put("ignore", "foob*");
        watchConfig.put("delay", "1000");

        String artifactId = "fooproject";

        odoWatchMojo.componentName = artifactId;
        odoWatchMojo.watch = watchConfig;
        odoWatchMojo.odo = odo;

        // When:
        odoWatchMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo watch fooproject --app myapp --project myproject --delay 1000 --ignore foob*");
    }

    @Test
    public void testMojoBehaviorMinimal(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoWatchMojo odoWatchMojo = new OdoWatchMojo();
        Odo odo = new Odo(odoExecutorStub);

        odoWatchMojo.odo = odo;

        // When:
        odoWatchMojo.execute();

        // Then:gi
        assertThat(odoExecutorStub).hasExecuted("odo watch");
    }

    @Test
    public void testMojoBehaviorWithDelay(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoWatchMojo odoWatchMojo = new OdoWatchMojo();
        Odo odo = new Odo(odoExecutorStub);

        odoWatchMojo.odo = odo;
        odoWatchMojo.delay = Integer.valueOf("1000");
        // When:
        odoWatchMojo.execute();

        // Then:gi
        assertThat(odoExecutorStub).hasExecuted("odo watch --delay 1000");
    }

}
