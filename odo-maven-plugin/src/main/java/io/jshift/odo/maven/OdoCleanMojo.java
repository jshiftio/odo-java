package io.jshift.odo.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "clean-io.jshift.odo")
public class OdoCleanMojo extends AbstractMojo {
    @Override
    public void execute() {
        OdoFactory.removeOdo();
    }
}
