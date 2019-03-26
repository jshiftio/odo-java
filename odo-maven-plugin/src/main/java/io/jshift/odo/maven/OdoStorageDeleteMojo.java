package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.StorageDeleteCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;

@Mojo(name = "delete-storage")
public class OdoStorageDeleteMojo extends AbstractMojo {
    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> deleteStorage;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!deleteStorage.containsKey("storageName")) {
            throw new IllegalArgumentException("storageName property is required for delete storage.");
        }

        StorageDeleteCommand storageDeleteCommand = odo.deleteStorage(deleteStorage.get("storageName")).build();
        ConfigurationInjector.injectFields(storageDeleteCommand, deleteStorage);
        storageDeleteCommand.execute(project.getBasedir().toPath());
    }
}
