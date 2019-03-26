package io.jshift.odo.maven;

import io.jshift.odo.assertj.OdoExecutorAssertion;
import io.jshift.odo.core.Odo;
import io.jshift.odo.junit5.OdoExecutorStubInjector;
import io.jshift.odo.odo.OdoExecutorStub;
import java.io.File;
import java.util.Arrays;
import org.apache.maven.model.Dependency;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OdoExecutorStubInjector.class})
public class OdoDetectDeployMojoTest {

    @Mock
    MavenProject project;

    @Test
    public void should_detect_and_apply_odo_commands(OdoExecutorStub odoExecutorStub) {

        // Given

        final Dependency postgresqlDependency = new Dependency();
        postgresqlDependency.setGroupId("org.postgresql");
        postgresqlDependency.setArtifactId("postgresql");
        postgresqlDependency.setScope("compile");

        when(project.getDependencies()).thenReturn(Arrays.asList(postgresqlDependency));
        when(project.getBasedir()).thenReturn(new File(".").getAbsoluteFile());
        when(project.getPackaging()).thenReturn("jar");

        final OdoDetectDeployMojo odoDetectDeployMojo = new OdoDetectDeployMojo();
        final Odo odo = new Odo(odoExecutorStub);

        odoDetectDeployMojo.odo = odo;
        odoDetectDeployMojo.project = project;

        // When

        odoDetectDeployMojo.execute();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo service create postgresql-persistent --plan default --wait",
            "odo component create openjdk18 --wait",
            "odo component push",
            "odo url create --port 8080",
            "odo component link postgresql-persistent --wait");
    }

}
