package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.StorageMountCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name="mount-storage")
public class OdoStorageMountMojo extends AbstractMojo {

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> mountStorage;

    @Parameter(required = true)
    protected String storageName;

    @Parameter(required = true)
    protected String path;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(odo == null) {
            odo = new Odo();
        }
        StorageMountCommand storageMountCommand = odo.mountStorage(storageName)
                .withPath(project.getBasedir().getAbsolutePath().concat(path)).build();

        injectFields(storageMountCommand, mountStorage, logger);
        storageMountCommand.execute(project.getBasedir().toPath());
    }

}
