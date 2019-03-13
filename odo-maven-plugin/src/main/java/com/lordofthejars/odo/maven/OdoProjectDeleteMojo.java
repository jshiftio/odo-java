package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ProjectDeleteCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "delete-project")
public class OdoProjectDeleteMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter(required = true)
    protected String projectName;

    @Parameter
    protected Map<String, String> projectDelete;

    @Override
    public void execute() {
        if (odo == null) {
            odo = new Odo();
        }

        if (!projectDelete.containsKey("projectName")) {
            throw new IllegalArgumentException("projectName property is required for delete project.");
        }

        ProjectDeleteCommand projectDeleteCommand = odo.deleteProject(projectDelete.get("projectName")).build();
        injectFields(projectDeleteCommand, projectDelete);

        projectDeleteCommand.execute(project.getBasedir().toPath());
    }
}
