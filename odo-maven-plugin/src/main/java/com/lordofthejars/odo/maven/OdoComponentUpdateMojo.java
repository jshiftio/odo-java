package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentUpdateCommand;
import com.lordofthejars.odo.maven.util.MavenArtifactsUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "update-component")
public class OdoComponentUpdateMojo extends AbstractMojo {
    private static final String PREFIX = "s";

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> updateComponent;

    @Parameter
    protected String artifactId;

    @Parameter
    protected String local;

    @Parameter
    protected String git;

    @Parameter
    protected String binary;

    @Override
    public void execute() {
        boolean flag = false;

        if(odo == null) {
            odo = new Odo();
        }

        ComponentUpdateCommand.Builder builder = odo.updateComponent().withComponentName(artifactId != null ? artifactId : MavenArtifactsUtil.getSanitizedArtifactId(project, PREFIX));

        if (local != null && local.length() > 0) {
            builder.withLocal(project.getBasedir().getAbsolutePath().concat(local));
            flag = true;
        }

        if (git != null && git.length() > 0 && !flag) {
            builder.withGit(git);
            flag = true;
        }

        if (binary != null && binary.length() > 0 && !flag) {
            builder.withBinary(project.getBasedir().getAbsolutePath().concat(binary));
            flag = true;
        }

        if (!flag) {
            builder.withLocal(project.getBasedir().getAbsolutePath());
        }

        ComponentUpdateCommand componentUpdateCommand = builder.build();
        injectFields(componentUpdateCommand, updateComponent, logger);
        componentUpdateCommand.execute();
    }
}
