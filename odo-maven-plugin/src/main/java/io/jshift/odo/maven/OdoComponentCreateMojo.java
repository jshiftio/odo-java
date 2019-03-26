package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.ComponentCreateCommand;
import io.jshift.odo.maven.util.MavenArtifactsUtil;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "create-component")
public class OdoComponentCreateMojo extends AbstractMojo {

    private static final String PREFIX = "s";

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> createComponent;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!createComponent.containsKey("componentType")) {
            throw new IllegalArgumentException("componentType property is required for create component.");
        }

        ComponentCreateCommand componentCreateCommand = odo.createComponent(createComponent.get("componentType"))
                .withComponentName(createComponent.get("componentName") != null ? createComponent.get("componentName") : MavenArtifactsUtil
                    .getSanitizedArtifactId(project, PREFIX))
                .build();

        ConfigurationInjector.injectFields(componentCreateCommand, createComponent);
        componentCreateCommand.execute(project.getBasedir().toPath());
    }
}