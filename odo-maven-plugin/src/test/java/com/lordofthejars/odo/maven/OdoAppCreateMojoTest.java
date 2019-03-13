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
public class OdoAppCreateMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehaviorWithName(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoAppCreateMojo odoAppCreateMojo = new OdoAppCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));

        Map<String, String> appCreateConfig = new HashMap<>();

        appCreateConfig.put("project", "xyz");
        appCreateConfig.put("appName", "myapp");

        odoAppCreateMojo.project = project;
        odoAppCreateMojo.odo = odo;
        odoAppCreateMojo.createApp = appCreateConfig;

        // When:
        odoAppCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo app create myapp --project xyz");
    }

    @Test
    public void testMojoBehaviorWithoutName(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoAppCreateMojo odoAppCreateMojo = new OdoAppCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));

        Map<String, String> appCreateConfig = new HashMap<String, String>() {{ put("project", "xyz"); }};

        odoAppCreateMojo.project = project;
        odoAppCreateMojo.odo = odo;
        odoAppCreateMojo.createApp = appCreateConfig;

        // When:
        odoAppCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo app create --project xyz");
    }
}
