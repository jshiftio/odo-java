package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.ProjectCreateCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "create-project")
public class OdoProjectCreateMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> projectCreate;


    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!projectCreate.containsKey("projectName")) {
            throw new IllegalArgumentException("projectName property is required for delete project.");
        }

        ProjectCreateCommand projectCreateCommand = odo.createProject(projectCreate.get("projectName")).build();
        ConfigurationInjector.injectFields(projectCreateCommand, projectCreate);

        projectCreateCommand.execute(project.getBasedir().toPath());
    }
}
