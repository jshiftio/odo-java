package io.jshift.odo.maven.util;

import org.apache.maven.project.MavenProject;

public class MavenArtifactsUtil {
    /**
     * ArtifactId is used for setting a resource name (service, pod,...) in Kubernetes resource.
     * The problem is that a Kubernetes resource name must start by a char.
     * This method returns a valid string to be used as Kubernetes name.
     * @param project MavenProject
     * @param prefix String
     * @return Sanitized Kubernetes name.
     */
    public static String getSanitizedArtifactId(MavenProject project, String prefix) {
        if (project.getArtifactId() != null && !project.getArtifactId().isEmpty() && Character.isDigit(project.getArtifactId().charAt(0))) {
            return prefix + project.getArtifactId();
        }

        return project.getArtifactId();
    }
}
