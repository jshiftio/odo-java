package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.StorageCreateCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "create-storage")
public class OdoStorageCreateMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> createStorage;

    public void execute() {
        if (odo == null) {
            odo = new Odo();
        }

        if (!createStorage.containsKey("storageName")) {
            throw new IllegalArgumentException("storageName property is required for create storage.");
        }

        if (!createStorage.containsKey("path")) {
            throw new IllegalArgumentException("path property is required for create storage.");
        }

        StorageCreateCommand storageCreateCommand = odo.createStorage(createStorage.get("storageName"), createStorage.get("path"))
                .build();

        injectFields(storageCreateCommand, createStorage);
        storageCreateCommand.execute(project.getBasedir().toPath());
    }
}