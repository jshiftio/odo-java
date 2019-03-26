package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.StorageMountCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name="mount-storage")
public class OdoStorageMountMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> mountStorage;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!mountStorage.containsKey("storageName")) {
            throw new IllegalArgumentException("storageName property is required for mount storage.");
        }

        if (!mountStorage.containsKey("path")) {
            throw new IllegalArgumentException("path property is required for mount storage.");
        }

        StorageMountCommand storageMountCommand = odo
            .mountStorage(mountStorage.get("storageName"), mountStorage.get("path"))
            .build();

        ConfigurationInjector.injectFields(storageMountCommand, mountStorage);
        storageMountCommand.execute(project.getBasedir().toPath());
    }

}
