package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
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
public class OdoServiceCreateMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoServiceCreateMojo odoServiceCreateMojo = new OdoServiceCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> serviceCreateConfiguration = new HashMap<>();
         serviceCreateConfiguration.put("wait", "false");
         serviceCreateConfiguration.put("app", "fooapp");

        odoServiceCreateMojo.project = project;
        serviceCreateConfiguration.put("serviceType", "dh-postgresql-apb");
        serviceCreateConfiguration.put("serviceName",  "fooservice");
        serviceCreateConfiguration.put("planName", "dev");

        odoServiceCreateMojo.odo = odo;
        odoServiceCreateMojo.createService = serviceCreateConfiguration;

        // When:
        odoServiceCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo service create dh-postgresql-apb fooservice --plan dev --app fooapp");
    }
}
