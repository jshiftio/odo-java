package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import io.jshift.odo.assertj.OdoExecutorAssertion;
import io.jshift.odo.junit5.OdoExecutorStubInjector;
import io.jshift.odo.odo.OdoExecutorStub;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo service delete fooservice --force");
    }
}
