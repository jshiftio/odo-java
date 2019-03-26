package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentUnlinkCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "unlink-component")
public class OdoComponentUnlinkMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> unlinkComponent;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!unlinkComponent.containsKey("name")) {
            throw new IllegalArgumentException("name property is required for unlink component.");
        }

        ComponentUnlinkCommand componentUnlinkCommand = odo.unlinkComponent(unlinkComponent.get("name")).build();
        injectFields(componentUnlinkCommand, unlinkComponent);
        componentUnlinkCommand.execute(project.getBasedir().toPath());
    }

}
