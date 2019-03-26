package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.ComponentLinkCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

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
        ConfigurationInjector.injectFields(componentLinkCommand, linkComponent);
        componentLinkCommand.execute(project.getBasedir().toPath());
    }

}
