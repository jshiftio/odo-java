package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.StorageUnmountCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Parameter(required = true)
    protected String storageNameorPath;

    @Parameter
    protected Map<String, String> unmountStorage;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }
        String currentPath = project.getBasedir().getAbsolutePath();
        storageNameorPath = Files.isDirectory(Paths.get(project.getBasedir().getAbsolutePath().concat(storageNameorPath))) ?
                currentPath.concat(storageNameorPath) : storageNameorPath;
        StorageUnmountCommand storageUnmountCommand = odo.unmountStorage(storageNameorPath).build();
        injectFields(storageUnmountCommand, unmountStorage, logger);
        storageUnmountCommand.execute(project.getBasedir().toPath());
    }
}
