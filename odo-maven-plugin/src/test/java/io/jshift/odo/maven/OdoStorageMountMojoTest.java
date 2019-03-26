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
public class OdoStorageMountMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoStorageMountMojo odoStorageMountMojo = new OdoStorageMountMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> mountStorageConfig = new HashMap<>();
        mountStorageConfig.put("app", "myapp");
        mountStorageConfig.put("component", "mycomponent");
        mountStorageConfig.put("project", "myproject");
        mountStorageConfig.put("path", "/storage/foo");
        mountStorageConfig.put("storageName", "mystorage");

        odoStorageMountMojo.project = project;
        odoStorageMountMojo.mountStorage = mountStorageConfig;

        odoStorageMountMojo.odo = odo;

        // When:
        odoStorageMountMojo.execute();

        // Then:
        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo storage mount mystorage --app myapp --component mycomponent --project myproject --path /storage/foo");
    }
}
