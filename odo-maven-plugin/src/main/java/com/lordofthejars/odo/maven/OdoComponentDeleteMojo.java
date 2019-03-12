package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentDeleteCommand;
import com.lordofthejars.odo.maven.util.MavenArtifactsUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "delete-component")
public class OdoComponentDeleteMojo extends AbstractMojo {
    private static final String PREFIX = "s";

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected String componentName;

    @Parameter
    protected Map<String, String> deleteComponent;

    @Parameter
    protected String app;

    @Parameter(defaultValue = "false")
    protected Boolean forceDeletion = false;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }

        ComponentDeleteCommand componentDeleteCommand = odo
                .deleteComponent(componentName != null ? componentName : MavenArtifactsUtil.getSanitizedArtifactId(project, PREFIX))
                .withForce(forceDeletion)
                .build();
        injectFields(componentDeleteCommand, deleteComponent, logger);
        componentDeleteCommand.execute(project.getBasedir().toPath());
    }
}
