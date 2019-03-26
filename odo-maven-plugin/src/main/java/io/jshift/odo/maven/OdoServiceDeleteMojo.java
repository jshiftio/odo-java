package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.ServiceDeleteCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;

@Mojo(name = "delete-service")
public class OdoServiceDeleteMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> deleteService;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!deleteService.containsKey("serviceName")) {
            throw new IllegalArgumentException("serviceType property is required for delete service.");
        }

        final ServiceDeleteCommand serviceDeleteCommand = odo.deleteService(deleteService.get("serviceName"))
                .build();

        ConfigurationInjector.injectFields(serviceDeleteCommand, deleteService);
        serviceDeleteCommand.execute(project.getBasedir().toPath());
    }
}
