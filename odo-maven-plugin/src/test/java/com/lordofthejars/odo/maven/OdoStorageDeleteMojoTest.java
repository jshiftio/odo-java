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
public class OdoStorageDeleteMojoTest {

    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoStorageDeleteMojo odoStorageDeleteMojo = new OdoStorageDeleteMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> storageDeleteConfiguration = new HashMap<>();
        storageDeleteConfiguration.put("app", "myapp");
        storageDeleteConfiguration.put("component", "mycomponent");
        storageDeleteConfiguration.put("project", "myproject");
        storageDeleteConfiguration.put("storageName", "foostorage");

        odoStorageDeleteMojo.deleteStorage = storageDeleteConfiguration;
        odoStorageDeleteMojo.odo = odo;
        odoStorageDeleteMojo.project = project;

        // When:
        odoStorageDeleteMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo storage delete foostorage --app myapp --component mycomponent --project myproject --force");
    }

}
