package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.AppDeleteCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

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
            odo = new Odo();
        }

        AppDeleteCommand appDeleteCommand = odo.deleteApp().build();

        injectFields(appDeleteCommand, deleteApp);
        appDeleteCommand.execute(project.getBasedir().toPath());
    }
}
