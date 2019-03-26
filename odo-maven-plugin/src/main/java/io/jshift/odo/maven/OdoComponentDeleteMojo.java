package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.ComponentDeleteCommand;
import io.jshift.odo.maven.util.MavenArtifactsUtil;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "delete-component")
public class OdoComponentDeleteMojo extends AbstractMojo {
    private static final String PREFIX = "s";

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> deleteComponent;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!deleteComponent.containsKey("componentName")) {
            throw new IllegalArgumentException("componentName property is required for delete component.");
        }

        ComponentDeleteCommand componentDeleteCommand = odo
                .deleteComponent(deleteComponent.get("componentName") != null ? deleteComponent.get("componentName") : MavenArtifactsUtil
                    .getSanitizedArtifactId(project, PREFIX))
                .build();
        ConfigurationInjector.injectFields(componentDeleteCommand, deleteComponent);
        componentDeleteCommand.execute(project.getBasedir().toPath());
    }
}
