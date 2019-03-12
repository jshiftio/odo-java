package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ServiceDeleteCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;
import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "delete-service")
public class OdoServiceDeleteMojo extends AbstractMojo {

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Parameter(required = true)
    protected String serviceName;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> deleteService;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }

        final ServiceDeleteCommand serviceDeleteCommand = odo.deleteService(serviceName)
                .build();

        injectFields(serviceDeleteCommand, deleteService, logger);
        serviceDeleteCommand.execute(project.getBasedir().toPath());
    }
}
