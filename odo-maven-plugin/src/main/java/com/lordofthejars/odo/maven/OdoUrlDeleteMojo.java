package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.UrlDeleteCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.logging.Logger;

@Mojo(name = "delete-url")
public class OdoUrlDeleteMojo extends AbstractMojo {

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Parameter
    protected String urlName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(odo == null) {
            odo = new Odo();
        }

        UrlDeleteCommand urlDeleteCommand = odo.deleteUrl(urlName).build();
        urlDeleteCommand.execute();
    }
}
