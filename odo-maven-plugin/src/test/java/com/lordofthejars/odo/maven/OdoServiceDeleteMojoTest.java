package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OdoExecutorStubInjector.class})
public class OdoServiceDeleteMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoServiceDeleteMojo odoServiceDeleteMojo = new OdoServiceDeleteMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> serviceDeleteConfiguration = new HashMap<>();
        serviceDeleteConfiguration.put("serviceName",  "fooservice");

        odoServiceDeleteMojo.project = project;
        odoServiceDeleteMojo.odo = odo;
        odoServiceDeleteMojo.deleteService = serviceDeleteConfiguration;

        // When:
        odoServiceDeleteMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo service delete fooservice --force");
    }
}
