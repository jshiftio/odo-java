package io.jshift.odo.detectors.extractor;

import io.jshift.odo.detectors.util.Packaging;
import java.nio.file.Paths;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MavenExtractorTest {


    @Test
    public void should_read_basic_pom_information() {

        // Given

        final Extractor extractor = new MavenExtractor(Paths.get("src/test/resources","test-pom.xml"));

        // When

        final String extractArtifactId = extractor.extractArtifactId();
        final Packaging extractTypeOfProject = extractor.extractTypeOfProject();

        // Then

        assertThat(extractArtifactId).isEqualTo("fabric8-maven-sample-spring-boot");
        assertThat(extractTypeOfProject).isEqualTo(Packaging.JAR);
    }

    @Test
    public void should_read_dependencies() {

        // Given

        final Extractor extractor = new MavenExtractor(Paths.get("src/test/resources","test-pom.xml"));

        // When

        final Set<Dependency> dependencies = extractor.extractDependencies();

        // Then

        Assertions.assertThat(dependencies)
            .containsExactlyInAnyOrder(
                new Dependency("org.springframework.boot", "spring-boot-starter-web"),
                new Dependency("org.springframework.boot", "spring-boot-starter-actuator"),
                new Dependency("org.jolokia", "jolokia-core")
            );
    }

}
