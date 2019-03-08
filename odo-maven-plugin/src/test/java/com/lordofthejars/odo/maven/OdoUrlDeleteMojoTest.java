package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;

@ExtendWith(OdoExecutorStubInjector.class)
public class OdoUrlDeleteMojoTest {
    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoUrlDeleteMojo odoUrlDeleteMojo = new OdoUrlDeleteMojo();
        Odo odo = new Odo(odoExecutorStub);

        odoUrlDeleteMojo.urlName = "test-url";
        odoUrlDeleteMojo.odo = odo;

        // When:
        odoUrlDeleteMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo url delete test-url");
    }

}
