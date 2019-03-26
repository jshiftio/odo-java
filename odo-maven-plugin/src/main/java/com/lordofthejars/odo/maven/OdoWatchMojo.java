package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.WatchCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "watch")
public class OdoWatchMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> watch;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        WatchCommand.Builder builder = odo.watch();

        WatchCommand watchCommand = builder.build();
        injectFields(watchCommand, watch);

        watchCommand.execute(project.getBasedir().toPath());
    }
}
