package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.StorageUnmountCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "unmount-storage")
public class OdoStorageUnmoutMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> unmountStorage;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!unmountStorage.containsKey("storageName")) {
            throw new IllegalArgumentException("storageName field is required to unmount the storage");
        }

        StorageUnmountCommand storageUnmountCommand = odo.unmountStorage(unmountStorage.get("storageName")).build();
        ConfigurationInjector.injectFields(storageUnmountCommand, unmountStorage);
        storageUnmountCommand.execute(project.getBasedir().toPath());
    }
}
