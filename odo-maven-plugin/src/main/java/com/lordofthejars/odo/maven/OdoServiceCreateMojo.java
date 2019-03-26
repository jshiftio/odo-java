package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ServiceCreateCommand;
import com.lordofthejars.odo.maven.util.MavenArtifactsUtil;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "create-service")
public class OdoServiceCreateMojo extends AbstractMojo {

    private static final String PREFIX = "s";

    protected Odo odo = null;

    @Parameter
    protected MavenProject project;

    @Parameter
    protected Map<String, String> createService;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!createService.containsKey("serviceType")) {
            throw new IllegalArgumentException("serviceType property is required for create service.");
        }

        if (!createService.containsKey("planName")) {
            throw new IllegalArgumentException("servicePlan property is required for create service.");
        }

        final ServiceCreateCommand serviceCreateCommand = odo.createService(createService.get("serviceType"), createService.get("planName"))
                .withServiceName(createService.get("serviceName") != null ? createService.get("serviceName") : MavenArtifactsUtil.getSanitizedArtifactId(project, PREFIX))
                .build();

        injectFields(serviceCreateCommand, createService);
        serviceCreateCommand.execute(project.getBasedir().toPath());
    }
}
