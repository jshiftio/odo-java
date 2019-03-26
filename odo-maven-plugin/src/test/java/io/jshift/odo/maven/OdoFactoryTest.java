package io.jshift.odo.maven;

import java.nio.file.Path;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class OdoFactoryTest {

    @Test
    public void should_copy_installation_to_configured_directory(@TempDir Path installation) {

        // Given

        final Path notCreatedDir = installation.resolve("not_created_dir");
        OdoFactory.odoLocalPath = notCreatedDir;
        // When

        OdoFactory.createOdo();

        // Then
        Assertions.assertThat(notCreatedDir.resolve(OdoFactory.ODO)).exists();

    }

}
