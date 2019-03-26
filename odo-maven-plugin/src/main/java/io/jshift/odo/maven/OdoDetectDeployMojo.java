package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import io.jshift.odo.detectors.DetectorManager;
import io.jshift.odo.detectors.extractor.Dependency;
import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.util.Packaging;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "detect-deploy")
public class OdoDetectDeployMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter(defaultValue = "false", property = "dryRun")
    protected boolean dryRun;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        final DetectorManager detectorManager = new DetectorManager(new MavenExtractor(project), odo);
        detectorManager.execute();
    }

    static class MavenExtractor implements Extractor {

        private MavenProject mavenProject;

        MavenExtractor(MavenProject mavenProject) {
            this.mavenProject = mavenProject;
        }

        @Override
        public Packaging extractTypeOfProject() {
            return Packaging.valueOf(this.mavenProject.getPackaging().toUpperCase());
        }

        @Override
        public String extractArtifactId() {
            return this.mavenProject.getArtifactId();
        }

        @Override
        public Set<Dependency> extractDependencies() {
            final HashSet<org.apache.maven.model.Dependency> dependencies = new HashSet<>(mavenProject.getDependencies());

            return dependencies.stream()
                .filter(d -> !"test".equals(d.getScope()))
                .map(d -> new Dependency(d.getGroupId(), d.getArtifactId()))
                .collect(Collectors.toSet());
        }

        @Override
        public Path workingDirectory() {
            return this.mavenProject.getBasedir().toPath();
        }
    }

}
