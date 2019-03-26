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
public class OdoAppDeleteMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoAppDeleteMojo odoAppDeleteMojo = new OdoAppDeleteMojo();
        Odo odo = new Odo(odoExecutorStub);

        Map<String, String> deleteAppConfiguration = new HashMap<>();
        deleteAppConfiguration.put("appName","myapp");
        deleteAppConfiguration.put("project", "xyz");

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        odoAppDeleteMojo.project = project;
        odoAppDeleteMojo.odo = odo;
        odoAppDeleteMojo.deleteApp = deleteAppConfiguration;

        // When:
        odoAppDeleteMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo app delete myapp --project xyz --force");
    }
}