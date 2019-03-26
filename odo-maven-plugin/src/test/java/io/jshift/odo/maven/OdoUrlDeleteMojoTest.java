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
public class OdoUrlDeleteMojoTest {

    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoUrlDeleteMojo odoUrlDeleteMojo = new OdoUrlDeleteMojo();
        Odo odo = new Odo(odoExecutorStub);

        Map<String, String> urlDeleteConfig = new HashMap<>();
        urlDeleteConfig.put("urlName", "test-url");

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        odoUrlDeleteMojo.odo = odo;
        odoUrlDeleteMojo.deleteUrl = urlDeleteConfig;
        odoUrlDeleteMojo.project = project;

        // When:
        odoUrlDeleteMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo url delete test-url");
    }

}
