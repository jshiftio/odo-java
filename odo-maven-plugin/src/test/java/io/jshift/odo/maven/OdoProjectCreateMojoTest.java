
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
public class OdoProjectCreateMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        OdoProjectCreateMojo odoProjectCreateMojo = new OdoProjectCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        Map<String, String> projectCreateConfiguration = new HashMap<>();
        projectCreateConfiguration.put("projectName", "fooproject");

        odoProjectCreateMojo.odo = odo;
        odoProjectCreateMojo.project = project;
        odoProjectCreateMojo.projectCreate = projectCreateConfiguration;

        // When:
        odoProjectCreateMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo project create fooproject");
    }
}
