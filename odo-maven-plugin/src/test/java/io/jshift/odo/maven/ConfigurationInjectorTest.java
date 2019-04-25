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
public class ConfigurationInjectorTest {

    @Mock
    MavenProject project;

    @Test
    public void should_inject_global_parameters(OdoExecutorStub odoExecutorStub) {

        // Given
        OdoAppCreateMojo odoAppCreateMojo = new OdoAppCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));

        Map<String, String> appCreateConfig = new HashMap<>();

        appCreateConfig.put("project", "xyz");
        appCreateConfig.put("appName", "myapp");
        appCreateConfig.put("alsologtostderr", "true");

        odoAppCreateMojo.project = project;
        odoAppCreateMojo.odo = odo;
        odoAppCreateMojo.createApp = appCreateConfig;

        // When:
        odoAppCreateMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo app create myapp --project xyz --alsologtostderr");

    }

    @Test
    public void should_inject_command_parameters(OdoExecutorStub odoExecutorStub) {

        // Given

        OdoComponentCreateMojo odoComponentCreateMojo = new OdoComponentCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));

        Map<String, String> createComponentConfig = new HashMap<>();

        createComponentConfig.put("componentType", "xyz");
        createComponentConfig.put("componentName", "zyx");
        createComponentConfig.put("context", "ctx");

        odoComponentCreateMojo.project = project;
        odoComponentCreateMojo.odo = odo;
        odoComponentCreateMojo.createComponent = createComponentConfig;

        // When

        odoComponentCreateMojo.execute();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo component create xyz zyx --context ctx");


    }

}
