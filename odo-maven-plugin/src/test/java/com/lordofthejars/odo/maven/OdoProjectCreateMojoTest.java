
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

import java.io.File;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;
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
        assertThat(odoExecutorStub).hasExecuted("odo project create fooproject");
    }
}
