package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentLinkCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "link-component")
public class OdoComponentLinkMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> linkComponent;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!linkComponent.containsKey("name")) {
            throw new IllegalArgumentException("name property is required for link component.");
        }

        ComponentLinkCommand componentLinkCommand = odo.linkComponent(linkComponent.get("name")).build();
        injectFields(componentLinkCommand, linkComponent);
        componentLinkCommand.execute(project.getBasedir().toPath());
    }

}
