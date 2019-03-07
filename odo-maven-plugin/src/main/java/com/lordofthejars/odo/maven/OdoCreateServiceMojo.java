package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ServiceCreateCommand;
import com.lordofthejars.odo.maven.util.MavenArtifactsUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "create-service")
public class OdoCreateServiceMojo extends AbstractMojo {

    private static final String PREFIX = "s";

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(OdoComponentCreateMojo.class.getName());

    @Parameter
    protected String serviceType;

    @Parameter
    protected String serviceName;

    @Parameter
    protected String servicePlan;

    @Parameter
    protected MavenProject project;

    @Parameter
    protected Map<String, String> createService;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }

        final ServiceCreateCommand serviceCreateCommand = odo.createService(serviceType, servicePlan)
                .withServiceName(serviceName != null ? serviceName : MavenArtifactsUtil.getSanitizedArtifactId(project, PREFIX))
                .withProject(project.getBasedir().getAbsolutePath())
                .build();

        injectFields(serviceCreateCommand, createService, logger);
        serviceCreateCommand.execute();
    }
}
