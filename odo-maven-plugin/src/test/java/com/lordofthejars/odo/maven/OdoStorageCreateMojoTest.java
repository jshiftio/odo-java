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
public class OdoStorageCreateMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
            // Given
        OdoStorageCreateMojo odoStorageCreateMojo = new OdoStorageCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> storageCreateConfiguration = new HashMap<>();
        storageCreateConfiguration.put("component", "mycomponent");
        storageCreateConfiguration.put("app", "myapp");
        storageCreateConfiguration.put("project", "myproject");
        String storageName = "foostorage";
        String size = "1Gi";
        String path = "/storage/foovolume";


        odoStorageCreateMojo.project = project;
        odoStorageCreateMojo.path = path;
        odoStorageCreateMojo.size = size;
        odoStorageCreateMojo.createStorage = storageCreateConfiguration;
        odoStorageCreateMojo.storageName = storageName;

        odoStorageCreateMojo.odo = odo;

        // When:
        odoStorageCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo storage create foostorage --app myapp --component mycomponent --project myproject --path /tmp/foodir/storage/foovolume --size 1Gi");
    }

    @Test
    public void testMojoBehaviorMinimal(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoStorageCreateMojo odoStorageCreateMojo = new OdoStorageCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        String storageName = "foostorage";
        String size = "1Gi";
        String path = "/storage/foovolume";


        odoStorageCreateMojo.project = project;
        odoStorageCreateMojo.path = path;
        odoStorageCreateMojo.size = size;
        odoStorageCreateMojo.storageName = storageName;

        odoStorageCreateMojo.odo = odo;

        // When:
        odoStorageCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo storage create foostorage --path /tmp/foodir/storage/foovolume --size 1Gi");
    }
}
