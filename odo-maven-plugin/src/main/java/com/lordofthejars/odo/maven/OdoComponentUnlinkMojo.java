package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentUnlinkCommand;
import com.lordofthejars.odo.maven.util.MavenArtifactsUtil;
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

    @Parameter
    protected String component;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }

        ComponentUnlinkCommand componentUnlinkCommand = odo.unlinkComponent(component != null ? component : MavenArtifactsUtil.getSanitizedArtifactId(project, PREFIX)) .build();
        injectFields(componentUnlinkCommand, unlinkComponent, logger);
        componentUnlinkCommand.execute();
    }

}
