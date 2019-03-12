package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OdoExecutorStubInjector.class})
public class OdoStorageDeleteMojoTest {

    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoStorageDeleteMojo odoStorageDeleteMojo = new OdoStorageDeleteMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> storageDeleteConfiguration = new HashMap<>();
        storageDeleteConfiguration.put("app", "myapp");
        storageDeleteConfiguration.put("component", "mycomponent");
        storageDeleteConfiguration.put("project", "myproject");
        String storagename = "foostorage";

        odoStorageDeleteMojo.forceDeletion = true;
        odoStorageDeleteMojo.deleteStorage = storageDeleteConfiguration;
        odoStorageDeleteMojo.storageName = storagename;
        odoStorageDeleteMojo.odo = odo;
        odoStorageDeleteMojo.project = project;

        // When:
        odoStorageDeleteMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo storage delete foostorage --app myapp --component mycomponent --project myproject --force");
    }

    @Test
    public void testMojoBehaviorMinimal(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoStorageDeleteMojo odoStorageDeleteMojo = new OdoStorageDeleteMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        String storagename = "foostorage";

        odoStorageDeleteMojo.storageName = storagename;
        odoStorageDeleteMojo.odo = odo;
        odoStorageDeleteMojo.project = project;

        // When:
        odoStorageDeleteMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo storage delete foostorage");
    }
}
