package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;

@ExtendWith({OdoExecutorStubInjector.class})
public class OdoStorageDeleteMojoTest {

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoStorageDeleteMojo odoStorageDeleteMojo = new OdoStorageDeleteMojo();
        Odo odo = new Odo(odoExecutorStub);

        Map<String, String> storageDeleteConfiguration = new HashMap<>();
        storageDeleteConfiguration.put("app", "myapp");
        storageDeleteConfiguration.put("component", "mycomponent");
        storageDeleteConfiguration.put("project", "myproject");
        String storagename = "foostorage";

        odoStorageDeleteMojo.forceDeletion = true;
        odoStorageDeleteMojo.deleteStorage = storageDeleteConfiguration;
        odoStorageDeleteMojo.storageName = storagename;
        odoStorageDeleteMojo.odo = odo;

        // When:
        odoStorageDeleteMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo storage delete foostorage --app myapp --component mycomponent --project myproject --force");
    }
}
