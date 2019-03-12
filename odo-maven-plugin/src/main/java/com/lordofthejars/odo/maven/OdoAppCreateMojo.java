package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.AppCreateCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "create-app")
public class OdoAppCreateMojo extends AbstractMojo {

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> createApp;

    @Parameter
    protected String appName;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }

        AppCreateCommand.Builder builder = odo.createApp();

        if (appName != null && appName.length() > 0) builder.withAppName(appName);

        AppCreateCommand appCreateCommand = builder.build();

        injectFields(appCreateCommand, createApp, logger);
        appCreateCommand.execute(project.getBasedir().toPath());
    }
}
