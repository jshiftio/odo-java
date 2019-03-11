package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.AppCreateCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

@Mojo(name = "create-app")
public class OdoAppCreateMojo extends AbstractMojo {

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> createApp;

    @Parameter(required=true)
    protected String appName;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }
        AppCreateCommand appCreateCommand = odo.createApp()
                .withAppName(appName)
                .withProject(project.getBasedir().getAbsolutePath())
                .build();

        if(createApp != null) {
            Class<?> c = appCreateCommand.getClass();
            for (Map.Entry<String, String> entry : createApp.entrySet()) {
                try {
                    ConfigurationInjector.copy(appCreateCommand, c, entry);
                } catch (IllegalStateException exception) {
                    logger.warning(exception.getMessage());
                }
            }
        }
        appCreateCommand.execute();
    }
}
