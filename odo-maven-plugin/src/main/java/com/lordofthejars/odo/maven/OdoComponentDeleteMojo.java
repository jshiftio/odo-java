package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentDeleteCommand;
import com.lordofthejars.odo.maven.util.MavenArtifactsUtil;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

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
            odo = new Odo();
        }

        if (!deleteComponent.containsKey("componentName")) {
            throw new IllegalArgumentException("componentName property is required for delete component.");
        }

        ComponentDeleteCommand componentDeleteCommand = odo
                .deleteComponent(deleteComponent.get("componentName") != null ? deleteComponent.get("componentName") : MavenArtifactsUtil.getSanitizedArtifactId(project, PREFIX))
                .build();
        injectFields(componentDeleteCommand, deleteComponent);
        componentDeleteCommand.execute(project.getBasedir().toPath());
    }
}
