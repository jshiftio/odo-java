package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.AppDeleteCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;

@Mojo(name = "delete-app")
public class OdoAppDeleteMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> deleteApp;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        AppDeleteCommand appDeleteCommand = odo.deleteApp().build();

        ConfigurationInjector.injectFields(appDeleteCommand, deleteApp);
        appDeleteCommand.execute(project.getBasedir().toPath());
    }
}
