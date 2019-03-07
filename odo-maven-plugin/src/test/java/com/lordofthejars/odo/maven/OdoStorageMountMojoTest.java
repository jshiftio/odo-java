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
public class OdoStorageMountMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoStorageMountMojo odoStorageMountMojo = new OdoStorageMountMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> mountStorageConfig = new HashMap<>();
        mountStorageConfig.put("app", "myapp");
        mountStorageConfig.put("component", "mycomponent");
        mountStorageConfig.put("project", "myproject");

        String path = "/storage/foo";
        String storagename = "mystorage";
        odoStorageMountMojo.project = project;
        odoStorageMountMojo.mountStorage = mountStorageConfig;
        odoStorageMountMojo.path = path;
        odoStorageMountMojo.storageName = storagename;

        odoStorageMountMojo.odo = odo;

        // When:
        odoStorageMountMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo storage mount mystorage --app myapp --component mycomponent --project myproject --path /tmp/foodir/storage/foo");
    }
}
