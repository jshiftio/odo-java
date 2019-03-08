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

@ExtendWith(OdoExecutorStubInjector.class)
public class OdoUrlCreateMojoTest {

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoUrlCreateMojo odoUrlCreateMojo = new OdoUrlCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        Map<String, String> createUrlConfig = new HashMap<>();
        createUrlConfig.put("port", "8080");
        createUrlConfig.put("component", "test-component");

        odoUrlCreateMojo.createUrl = createUrlConfig;
        odoUrlCreateMojo.urlName = "test-url";
        odoUrlCreateMojo.odo = odo;

        // When:
        odoUrlCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo url create test-url --component test-component --port 8080");
    }

    @Test
    public void testMojoBehaviorOnlyPort(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoUrlCreateMojo odoUrlCreateMojo = new OdoUrlCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        Map<String, String> createUrlConfig = new HashMap<>();
        createUrlConfig.put("port", "8080");

        odoUrlCreateMojo.createUrl = createUrlConfig;
        odoUrlCreateMojo.odo = odo;

        // When:
        odoUrlCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo url create --port 8080");
    }

    @Test
    public void testMojoBehaviorOnlyUrlName(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoUrlCreateMojo odoUrlCreateMojo = new OdoUrlCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        odoUrlCreateMojo.urlName = "test-url";
        odoUrlCreateMojo.odo = odo;

        // When:
        odoUrlCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo url create test-url");
    }
}
