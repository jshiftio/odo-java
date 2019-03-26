package io.jshift.odo.maven;

import io.jshift.odo.core.Odo;
import io.jshift.odo.core.commands.UrlDeleteCommand;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "delete-url")
public class OdoUrlDeleteMojo extends AbstractMojo {

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> deleteUrl;

    @Override
    public void execute() {

        if (odo == null) {
            odo = OdoFactory.createOdo();
        }

        if (!deleteUrl.containsKey("urlName")) {
            throw new IllegalArgumentException("urlName field is required to delete the url");
        }

        UrlDeleteCommand urlDeleteCommand = odo.deleteUrl(deleteUrl.get("urlName")).build();
        urlDeleteCommand.execute(project.getBasedir().toPath());
    }
}
