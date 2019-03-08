package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.UrlCreateCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "create-url")
public class OdoUrlCreateMojo extends AbstractMojo {

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Parameter
    protected Map<String, String> createUrl;

    @Parameter
    protected String urlName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(odo == null) {
            odo = new Odo();
        }

        UrlCreateCommand.Builder builder = odo.createUrl();

        if (urlName != null && urlName.length() > 0) {
            builder.withName(urlName);
        }

        UrlCreateCommand urlCreateCommand = builder.build();

        injectFields(urlCreateCommand, createUrl, logger);
        urlCreateCommand.execute();
    }
}
