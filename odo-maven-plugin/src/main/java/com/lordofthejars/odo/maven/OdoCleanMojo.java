package com.lordofthejars.odo.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "clean-odo")
public class OdoCleanMojo extends AbstractMojo {
    @Override
    public void execute() {
        OdoFactory.removeOdo();
    }
}
