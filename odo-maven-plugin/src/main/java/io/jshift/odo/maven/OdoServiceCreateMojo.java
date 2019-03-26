package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.ServiceCreateCommand;
import java.util.Map;
import io.jshift.odo.maven.util.MavenArtifactsUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

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

        final ServiceCreateCommand
            serviceCreateCommand = odo.createService(createService.get("serviceType"), createService.get("planName"))
                .withServiceName(createService.get("serviceName") != null ? createService.get("serviceName") : MavenArtifactsUtil
                    .getSanitizedArtifactId(project, PREFIX))
                .build();

        ConfigurationInjector.injectFields(serviceCreateCommand, createService);
        serviceCreateCommand.execute(project.getBasedir().toPath());
    }
}
