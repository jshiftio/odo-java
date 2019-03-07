package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.StorageCreateCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "create-storage")
public class OdoStorageCreateMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Parameter
    protected String path;

    @Parameter
    protected String size;

    @Parameter
    protected Map<String, String> createStorage;

    @Parameter
    protected String storageName;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if(odo == null) {
            odo = new Odo();
        }

        StorageCreateCommand storageCreateCommand = odo.createStorage(storageName)
                .withPath(project.getBasedir().getAbsolutePath().concat(path)).withSize(size).build();

        injectFields(storageCreateCommand, createStorage, logger);
        storageCreateCommand.execute();
    }
}