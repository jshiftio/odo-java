package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.ComponentUpdateCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "update-component")
public class OdoComponentUpdateMojo extends AbstractMojo {
    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> updateComponent;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        ComponentUpdateCommand componentUpdateCommand = odo.updateComponent().build();

        ConfigurationInjector.injectFields(componentUpdateCommand, updateComponent);
        componentUpdateCommand.execute(project.getBasedir().toPath());
    }
}
