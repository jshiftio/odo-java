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
public class OdoComponentDeleteMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoComponentDeleteMojo odoComponentDeleteMojo = new OdoComponentDeleteMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> componentDeleteConfiguration = new HashMap<>();
        componentDeleteConfiguration.put("project", "fooproject");
        componentDeleteConfiguration.put("componentName", "mycomponent");

        odoComponentDeleteMojo.deleteComponent = componentDeleteConfiguration;
        odoComponentDeleteMojo.project = project;

        odoComponentDeleteMojo.odo = odo;

        // When:
        odoComponentDeleteMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo component delete mycomponent --project fooproject --force");
    }
}