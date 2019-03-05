package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentCreateCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

@Mojo(name = "create-component")
public class OdoComponentCreateMojo extends AbstractMojo {

    private static final String PREFIX = "s";

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(OdoComponentCreateMojo.class.getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter(defaultValue = "openjdk18")
    protected String componentType;

    @Parameter
    protected Map<String, String> createComponent;

    @Parameter
    protected String artifactId;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(odo == null) {
            odo = new Odo();
        }
        ComponentCreateCommand componentCreateCommand = odo.createComponent(componentType)
                .withComponentName(artifactId != null ? artifactId : getSanitizedArtifactId())
                .withLocal(project.getBasedir().getAbsolutePath())
                .build();

        if(createComponent != null) {
            Class<?> c = componentCreateCommand.getClass();
            for (Map.Entry<String, String> entry : createComponent.entrySet()) {
                try {
                    ConfigurationInjector.copy(componentCreateCommand, c, entry);
                } catch (IllegalStateException exception) {
                    logger.warning(exception.getMessage());
                }
            }
        }
        componentCreateCommand.execute();
    }

    /**
     * ArtifactId is used for setting a resource name (service, pod,...) in Kubernetes resource.
     * The problem is that a Kubernetes resource name must start by a char.
     * This method returns a valid string to be used as Kubernetes name.
     * @return Sanitized Kubernetes name.
     */
    private String getSanitizedArtifactId() {
        if (project.getArtifactId() != null && !project.getArtifactId().isEmpty() && Character.isDigit(project.getArtifactId().charAt(0))) {
            return PREFIX + project.getArtifactId();
        }

        return project.getArtifactId();
    }
}
