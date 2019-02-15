package com.lordofthejars.odo.core;

import com.lordofthejars.odo.api.Command;
import com.lordofthejars.odo.api.OdoConfiguration;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public class Odo {

    private static final Logger logger = Logger.getLogger(Odo.class.getName());
    private final OdoConfiguration odoConfiguration;

    private InstallManager installManager = new InstallManager();
    private OdoExecutor odoExecutor;

    protected Path odoHome;

    public Odo() {
        this(new OdoConfiguration());
    }

    public Odo(final OdoConfiguration odoConfiguration) {
        this.odoConfiguration = odoConfiguration;
        odoExecutor = new OdoExecutor((this.odoConfiguration));
        install();
    }

    protected void install() {
        try {
            odoHome = odoHome == null ? installManager.install(this.odoConfiguration) : odoHome;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    // TODO when OdoClient change this class to package scope
    public List<String> execute(final Path projectDirectory, final Command command) {
        return odoExecutor.execute(odoHome, projectDirectory, command);
    }

    // TODO when OdoClient change this class to package scope
    public List<String> execute(final Command command) {
        return odoExecutor.execute(odoHome, command);
    }

}
