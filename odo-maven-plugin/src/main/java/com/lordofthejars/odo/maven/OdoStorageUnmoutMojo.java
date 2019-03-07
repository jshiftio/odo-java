package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.StorageUnmountCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "unmount-storage")
public class OdoStorageUnmoutMojo extends AbstractMojo {

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected String storageName;

    @Parameter
    protected Map<String, String> unmountStorage;

    @Parameter
    protected String path;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }

        StorageUnmountCommand.Builder builder = odo.unmountStorage(storageName);

        if (path!= null && path.length() > 0) {
            builder.withPath(project.getBasedir().getAbsolutePath().concat(path));
        }

        StorageUnmountCommand storageUnmountCommand = builder.build();
        injectFields(storageUnmountCommand, unmountStorage, logger);
        storageUnmountCommand.execute();
    }
}
