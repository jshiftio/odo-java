package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentCreateCommand;
import com.lordofthejars.odo.maven.util.MavenArtifactsUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "create-component")
public class OdoComponentCreateMojo extends AbstractMojo {

    private static final String PREFIX = "s";

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter(defaultValue = "openjdk18", required = true)
    protected String componentType;

    @Parameter
    protected Map<String, String> createComponent;

    @Parameter
    protected String componentName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(odo == null) {
            odo = new Odo();
        }

        ComponentCreateCommand componentCreateCommand = odo.createComponent(componentType)
                .withComponentName(componentName != null ? componentName : MavenArtifactsUtil.getSanitizedArtifactId(project, PREFIX))
                .withLocal(project.getBasedir().getAbsolutePath())
                .build();

        injectFields(componentCreateCommand, createComponent, logger);
        componentCreateCommand.execute(project.getBasedir().toPath());
    }
}