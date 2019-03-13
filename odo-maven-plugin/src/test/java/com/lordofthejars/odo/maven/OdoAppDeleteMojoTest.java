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
        assertThat(odoExecutorStub).hasExecuted("odo app delete myapp --project xyz --force");
    }
}