package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.UrlCreateCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "create-url")
public class OdoUrlCreateMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> createUrl;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        UrlCreateCommand.Builder builder = odo.createUrl();
        UrlCreateCommand urlCreateCommand = builder.build();

        ConfigurationInjector.injectFields(urlCreateCommand, createUrl);
        urlCreateCommand.execute(project.getBasedir().toPath());
    }
}
