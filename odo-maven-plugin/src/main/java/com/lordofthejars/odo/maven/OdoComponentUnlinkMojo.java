package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentUnlinkCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "unlink-component")
public class OdoComponentUnlinkMojo extends AbstractMojo {
    private static final String PREFIX = "s";

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> unlinkComponent;

    // The target component/service name to remove link with
    @Parameter(required = true)
    protected String target;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }

        ComponentUnlinkCommand componentUnlinkCommand = odo.unlinkComponent(target).build();
        injectFields(componentUnlinkCommand, unlinkComponent, logger);
        componentUnlinkCommand.execute(project.getBasedir().toPath());
    }

}
