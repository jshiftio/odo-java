package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.detectors.DetectorManager;
import com.lordofthejars.odo.detectors.extractor.Dependency;
import com.lordofthejars.odo.detectors.extractor.Extractor;
import com.lordofthejars.odo.detectors.util.Packaging;
import com.lordofthejars.odo.maven.util.DryRunOdoExecutor;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
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
            if (dryRun) {
                odo = new Odo(new DryRunOdoExecutor());
            } else {
                odo = new Odo();
            }
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
