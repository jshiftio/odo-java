package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentPushCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "push-component")
public class OdoComponentPushMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> pushComponent;

    @Override
    public void execute() {
        if (odo == null) {
            odo = new Odo();
        }

        ComponentPushCommand componentPushCommand = odo.pushComponent().build();

        injectFields(componentPushCommand, pushComponent);
        componentPushCommand.execute(project.getBasedir().toPath());
    }
}
