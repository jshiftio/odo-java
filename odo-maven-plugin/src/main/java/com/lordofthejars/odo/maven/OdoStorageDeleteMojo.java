package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.StorageDeleteCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "delete-storage")
public class OdoStorageDeleteMojo extends AbstractMojo {
    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter(required = true)
    protected String storageName;

    @Parameter
    protected Map<String, String> deleteStorage;

    @Parameter(defaultValue = "false")
    protected Boolean forceDeletion = false;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }

        StorageDeleteCommand storageDeleteCommand = odo.deleteStorage(storageName).withForce(forceDeletion).build();
        injectFields(storageDeleteCommand, deleteStorage, logger);
        storageDeleteCommand.execute(project.getBasedir().toPath());
    }
}
