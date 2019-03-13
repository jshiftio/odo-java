package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.StorageUnmountCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

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
            odo = new Odo();
        }

        if (!unmountStorage.containsKey("storageName")) {
            throw new IllegalArgumentException("storageName field is required to unmount the storage");
        }

        StorageUnmountCommand storageUnmountCommand = odo.unmountStorage(unmountStorage.get("storageName")).build();
        injectFields(storageUnmountCommand, unmountStorage);
        storageUnmountCommand.execute(project.getBasedir().toPath());
    }
}
